import java.io.File


fun main() {
    val source = File("src/main/resources/day4.txt").readLines()
//    val source = """
//7,4,9,5,11,17,23,2,0,14,21,24,10,16,13,6,15,25,12,22,18,20,8,19,3,26,1
//
//22 13 17 11  0
// 8  2 23  4 24
//21  9 14 16  7
// 6 10  3 18  5
// 1 12 20 15 19
//
// 3 15  0  2 22
// 9 18 13 17  5
//19  8  7 25 23
//20 11 10 24  4
//14 21 16 12  6
//
//14 21 17 24  4
//10 16 15  9 19
//18  8 23 26 20
//22 11 13  6  5
// 2  0 12  3  7
//    """.trimIndent().split("\n")
    val state = parseFile(source)

    val snapshots = mutableListOf<Pair<Int, Board>>()
    state.drawOrder.forEach { draw ->
        state.boards.forEach { board ->
            if (!board.hasBingo()) {
                board.processDraw(draw)
                if (board.hasBingo()) {
//                    println("Part 1: ${board.score(draw)}")
                    snapshots.add(draw to board.copy())
                }
            }
        }
    }
    val firstWin = snapshots.first().let { (x, board) -> board.score(x) }
    val lastWin = snapshots.last().let { (x, board) -> board.score(x) }

    println("Part1: ${firstWin}")
    println("Part1: ${lastWin}")

//    println(state)
}

data class BingoState(
    val drawOrder: List<Int>,
    val boards: List<Board>
)

data class Board(
    val grid: List<List<Cell>>
) {
    private val reverseIndex = mutableMapOf<Int, MutableList<Cell>>()
    init {
        grid.forEachIndexed { i, list ->
            list.forEachIndexed { j, c: Cell ->
                val value = c.number
                if (value in reverseIndex) {
                    reverseIndex[value]!!.add(c)
                } else {
                    reverseIndex[value] = mutableListOf(c)
                }
            }
        }
    }

    fun score(x: Int): Int {
        return grid.flatMap { row -> row.filter { !it.marked } }.sumBy { it.number } * x
    }
    fun processDraw(x: Int) {
        if (x in reverseIndex) {
            reverseIndex[x]!!.forEach { it.marked = true }
        }
    }

    fun hasBingo(): Boolean {
        // column check
        for (i in 0..4) {
            val col = grid.map { it[i]}
            if (col.all { it.marked }) {
                return true
            }
        }

        grid.forEach { row ->
            if (row.all { it.marked }) {
                return true
            }
        }
        return false
    }
}

data class Cell(
    val number: Int,
    var marked: Boolean
)

fun parseFile(lines: List<String>): BingoState {
    val moves = lines.first().split(",").map { it.toInt() }
    val chunks = mutableListOf(mutableListOf<String>())
    lines.drop(2).forEach { line ->
        if (line.trim().isEmpty()) {
            chunks.add(mutableListOf())
        } else {
            chunks.last().add(line)
        }
    }

    val boards = chunks.map { boardLines ->
        val cellRows = boardLines.map { boardLine: String ->
            val lineSplit = boardLine.split(" ", " ")
            println("--$lineSplit--")
            lineSplit.filter{ it.isNotBlank() }.map { Cell(it.toInt(), false) }
        }
        Board(cellRows)
    }

    return BingoState(moves, boards)
}