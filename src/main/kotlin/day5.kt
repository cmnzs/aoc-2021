import java.io.File
import kotlin.math.min
import kotlin.math.max

fun main() {
    val source = File("src/main/resources/day5.txt").readLines()

    val x = source.map { parseLine(it) }.filter { it.isStraight() }

    val g = Grid()

    x.forEach { line ->
        g.fill(line)
    }
//    g.print()

    print("Part1: ${g.overlapTracker.count()} Values:")

}

data class Grid(
    private val m: MutableMap<Int, MutableMap<Int, Int>> = mutableMapOf()
) {
    val overlapTracker = mutableSetOf<Point>()

    var minX = 0
    var minY = 0

    var maxX = 0
    var maxY = 0

    fun print() {
        val s = mutableListOf<String>()
        for (i in minX..maxX) {
            for (j in minY..maxY) {
                if (i in m && j in m[i]!!) {
                    print(m[i]!![j]!!)
                } else {
                    print(".")
                }
            }
            print("\n")

        }
    }
    fun fill(pt: Point) {
        minX = min(minX, pt.x)
        maxX = max(maxX, pt.x)
        minY = min(minY, pt.y)
        maxY = max(maxY, pt.y)

        if (pt.x in m) {
            if (pt.y in m[pt.x]!!) {
                m[pt.x]!![pt.y] = m[pt.x]!![pt.y]!! + 1
                overlapTracker.add(pt)
            } else {
                m[pt.x]!![pt.y] = 1
            }
        } else {
            m[pt.x] = mutableMapOf(pt.y to 1)
        }
    }

    fun fill(line: Line) {
        line.interpolatePoints().forEach { fill(it) }
    }

}

data class Point(
    val x: Int,
    val y: Int
)

data class Line(
    val p1: Point,
    val p2: Point
) {
    fun isStraight(): Boolean {
        return (p1.x == p2.x) or (p1.y == p2.y)
    }

    fun interpolatePoints(): List<Point> {
        val acc = mutableListOf<Point>()
        val x1 = min(p1.x, p2.x)
        val x2 = max(p1.x, p2.x)

        val y1 = min(p1.y, p2.y)
        val y2 = max(p1.y, p2.y)

        for (i in x1..x2) {
            for (j in y1..y2) {
                acc.add(Point(j, i))
            }
        }
        return acc
    }
}
fun parseLine(s: String): Line {
    val x = s.split(" -> ").map { it.split(",") }.map { Point(it.first().toInt(), it.last().toInt()) }
    return Line(x.first(), x.last())
}