import java.io.File


fun main() {
    val source = File("src/main/resources/day4.txt").readLines()
    val state = parseFile(source)

    println(state)
}

data class BingoState(
    val drawOrder: List<Int>,
    val boards: List<Board>
)

data class Board(
    val grid: List<List<Cell>>
)

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
//    println(chunks)

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