package day11

import java.io.File

fun main() {

    val source = File("src/main/resources/day11.txt").readLines().map { it.toList().map { it.toString().toInt() }.toMutableList() }.toMutableList()
    println("Init State:")
    source.forEach { println(it) }
    println()
    var counter = 0
    var i = 0
    while(true) {
        i+=1
        step(source)
        val sumBy = source.sumBy { row -> row.count { it == 0 } }
        counter += sumBy
        if (i == 100) {
            println("Part1: $counter")
        }
        if (sumBy == 100) {
            println("Part2: Step $i $sumBy")
            break
        }
    }


}

fun step(mat: MutableList<MutableList<Int>>) {


    // increase all by 1
    for (i in 0 until mat.size) {
        for (j in 0 until mat.first().size) {
            mat[i][j] += 1
        }
    }
    val flashed = mutableSetOf<Pair<Int,Int>>()
    val stack = ArrayDeque<Pair<Int,Int>>()

    for (i in 0 until mat.size) {
        for (j in 0 until mat.first().size) {
            if (mat[i][j] > 9) {
                stack.add(Pair(i, j))
            }
        }
    }

    while (stack.isNotEmpty()) {
//        println(stack)

        val pt = stack.removeLast()
        if (pt in flashed) {
            continue
        }
        flashed.add(pt)
        val (i, j) = pt
        mat[i][j] = 0
        val offsets = listOf(Pair(0, 1), Pair(0, -1), Pair(1, 0), Pair(-1, 0), Pair(1, 1), Pair(-1, 1), Pair(1, -1), Pair(-1, -1))
        for (o in offsets) {
            val ii = i + o.first
            val jj = j + o.second
            val element = Pair(ii, jj)

            if (ii in 0 until mat.size && jj in 0 until  mat.first().size && element !in flashed) {
                mat[ii][jj] += 1

                if (mat[ii][jj] > 9 && (element !in flashed)) {
//                    println("adding $element to stack")
                    stack.add(element)
                }
            }
        }
    }
}


