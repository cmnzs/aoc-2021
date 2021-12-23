package day13

import java.io.File


fun main() {
    val source = File("src/main/resources/day13.txt").readLines()

    val coords = source.filter { !it.startsWith("fold") && it.isNotBlank() }.map { it.split(",").let { it.first().toInt() to it.last().toInt() } }
    val folds = source.filter { it.startsWith("fold") }.map { it.split("=").let { it.first().last() to it.last().toInt() } }.map { Line(it.first.toString(), it.second) }

    val applicableFold = folds.first()

//    coords.printGrid()
    println("Folding...")

    var nc = coords
    folds.forEach { fold ->
//        nc.printGrid()
        nc = fold(nc, fold)
    }
    nc.printGrid()

}
typealias Point = Pair<Int, Int>

data class Line(val orientation: String, val index: Int)

fun fold(coords: List<Point>, line: Line): List<Point> {
    return coords.map { it -> it.fold(line) }
}

fun Point.fold(line: Line): Point {
    when (line.orientation) {
        "x" -> {
            val newX = if (first < line.index) {
                first
            } else {
                val delta = (first - line.index)
                line.index - delta
            }
            return Point(newX, this.second)
        }
        "y" -> {
            val newY = if (second < line.index) {
                second
            } else {
                val delta = (second - line.index)
                line.index - delta
            }
            return Point(first, newY)
        }
        else -> {
            TODO()
        }
    }
}
fun List<Point>.printGrid() {
    val xs = 0 .. this.maxOf { it.first }
    val ys = 0 .. this.maxOf { it.second }

    val m = mutableMapOf<Int, MutableMap<Int, Int>>()

    forEach { (i,j) ->
        if (i in m) {
            val mm = m[i]!!
            mm[j] = 1
        } else {
            m[i] = mutableMapOf()
            val mm = m[i]!!
            mm[j] = 1
        }
    }
    for (j in ys) {
        for (i in xs) {
            if (i in m && j in m[i]!!) {
                print("#")
            } else {
                print(".")
            }
        }
        println()
    }
    println("M.count() = ${m.map{ it.value.count() }.sum()}")
}

