import java.io.File
import java.lang.Math.abs

fun main() {
    val source = File("src/main/resources/day7.txt")
        .readLines()
        .first()
        .split(",")
        .map { it.toInt() }


    val min = source.minOrNull()!!
    val max = source.maxOrNull()!!

    val range = min..max
    // position to cost
    val costMap = mutableMapOf<Int, Int>()
    var minCost = Int.MAX_VALUE
    var minPosition: Int? = null

    range.forEach { position ->
        val cost = source.sumBy { cost(kotlin.math.abs(it - position)) }
        costMap[position] = cost
        if (cost < minCost) {
            minCost = cost
            minPosition = position
        }
    }

    println("Part1: $costMap")
    println("Part1: $minCost @ $minPosition")

}

fun cost(n: Int): Int {
    return (1..n).sum()
}