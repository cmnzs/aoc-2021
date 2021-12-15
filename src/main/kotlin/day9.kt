import java.io.File
import kotlin.collections.ArrayDeque

data class Element(val r: Int, val c: Int) {
    private fun neighbours(): List<Element> {
        val up = Element(r - 1, c)
        val down = Element(r + 1, c)

        val left = Element(r, c - 1)
        val right = Element(r, c + 1)

        return listOf(up, down, left, right)
    }

    fun reachableNeighbours(rows: Int, cols: Int): List<Element> {
        return neighbours()
            .filter { (ri, ci) -> ri in 0 until rows && (ci in 0 until cols) }
    }
}

fun getBasins(lowestPoints: List<Element>, mat: List<List<Int>>): List<Set<Element>> {
    val l = mutableListOf<Set<Element>>()
    val visited = mutableSetOf<Element>()

    for (p in lowestPoints) {
        val stack = ArrayDeque<Element>()
        val basinSet = mutableSetOf<Element>()

        stack.addAll(p.reachableNeighbours(mat.size, mat.first().size).filter { mat[it.r][it.c] != 9 })
        while (stack.isNotEmpty()) {
            println(stack)
            val e = stack.removeLast()
            basinSet.add(e)
            visited.add(e)
            val newNeighbours = e.reachableNeighbours(mat.size, mat.first().size)
                .filter { mat[it.r][it.c] != 9 }
                .filter { !visited.contains(it) && !stack.contains(it) }
            stack.addAll(newNeighbours)
        }
        l.add(basinSet)
    }
    return l
}

fun main() {
    val source = File("src/main/resources/day9.txt").readLines()

    val mat = source.map { line -> line.toList().map { it.toString().toInt() } }

    val cols = mat.first().size
    val rows = mat.size

    val lowestPoints = mutableListOf<Element>()
    for (r in 0 until rows) {
        for (c in 0 until cols) {
            val isLowest = Element(r, c)
                .reachableNeighbours(rows, cols)
                .map { (ri, ci) -> mat[ri][ci] }
                .all { it > mat[r][c] }

            if (isLowest) {
                lowestPoints.add(Element(r, c))
            }
            mat[r][c]
        }
    }
    println("Part 1: ${lowestPoints.map { (r, c) -> mat[r][c] + 1 }.sum()}")
    println("Part 2: ${getBasins(lowestPoints, mat).map { it.size }.sortedDescending().slice(0..2).multiply()}")
}

fun List<Int>.multiply(): Int {
    return reduce { acc, i -> acc*i }
}