import java.io.File

fun main() {
    val source = File("src/main/resources/day9.txt").readLines()

    val mat = source.map { line -> line.toList().map { it.toString().toInt() } }

    val cols = mat.first().size
    val rows = mat.size

    val lowestPoints = mutableListOf<Pair<Int,Int>>()
    for (r in 0 until rows) {
        for (c in 0 until cols) {

            val up = Pair(r - 1, c )
            val down = Pair(r + 1, c )

            val left = Pair(r, c - 1)
            val right = Pair(r, c + 1)
            val isLowest = listOf(up, down, left, right)
                .filter { (ri, ci) -> ri in 0 until rows && (ci in 0 until cols)}
                .map { (ri, ci) -> mat[ri][ci] }
                .all { it > mat[r][c] }

            if (isLowest) {
                lowestPoints.add(Pair(r,c))
            }
            mat[r][c]
        }
    }

    println("Part 1: ${lowestPoints.map { (r,c) -> mat[r][c] + 1 }.sum()}")

}