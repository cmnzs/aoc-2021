import java.io.File
import kotlin.math.min
import kotlin.math.max
import kotlin.math.sign

fun main() {
    val source = File("src/main/resources/day5.txt").readLines()

    val straightLines = source.map { parseLine(it) }.filter { it.isStraight() }

    val g = Grid()

    straightLines.forEach { line ->
        g.fill(line)
    }

    println("Part1: ${g.overlapTracker.count()} Values:")


    source.map { parseLine(it) }.filter { !it.isStraight() }.also { it.forEach { println("Diagonal $it :: ${it.interpDiagonal()}") } }.forEach { line ->

        line.interpDiagonal().also{ println("Diag: $it")}
            .forEach { pt ->

            g.fill(pt)
        }

//        println("===")
//        g.print()

    }


    print("Part2: ${g.overlapTracker.count()} Values:")

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
                    print("${m[i]!![j]!!}")
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

        if (pt.y in m) {
            if (pt.x in m[pt.y]!!) {
                m[pt.y]!![pt.x] = m[pt.y]!![pt.x]!! + 1
                overlapTracker.add(pt)
            } else {
                m[pt.y]!![pt.x] = 1
            }
        } else {
            m[pt.y] = mutableMapOf(pt.x to 1)
        }
    }

    fun fill(line: Line) {
        line.interpolatePoints().forEach { fill(it) }
    }

    fun fill(list: List<Point>) {
        list.forEach { fill(it) }
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

    fun interpDiagonal(): List<Point> {
        val acc = mutableListOf<Point>()
        val x1 = p1.x
        val x2 = p2.x

        val y1 = p1.y
        val y2 = p2.y

        val dy = y2 - y1
        val dx = x2 - x1

        var p = Point(x1, y1)
        acc.add(p)

        while (acc.last() != Point(x2, y2)) {
            p = Point(p.x + (1 * dx.sign), p.y + (1 * dy.sign))
            acc.add(p)
        }

        return acc
    }
    fun interpolatePoints(): List<Point> {
        val acc = mutableListOf<Point>()
        val x1 = min(p1.x, p2.x)
        val x2 = max(p1.x, p2.x)

        val y1 = min(p1.y, p2.y)
        val y2 = max(p1.y, p2.y)

        for (i in x1..x2) {
            for (j in y1..y2) {
                acc.add(Point(i, j))
            }
        }
        return acc
    }
}
fun parseLine(s: String): Line {
    val x = s.split(" -> ").map { it.split(",") }.map { Point(it.first().toInt(), it.last().toInt()) }
    return Line(x.first(), x.last())
}