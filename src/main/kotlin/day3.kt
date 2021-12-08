import java.io.File
import java.lang.Exception
import java.lang.RuntimeException


fun main() {
    val source = File("src/main/resources/day3.txt").readLines()
//    val source = """
//        00100
//        11110
//        10110
//        10111
//        10101
//        01111
//        00111
//        11100
//        10000
//        11001
//        00010
//        01010
//    """.trimIndent().split("\n")
    val data = source.map { line ->
        line.trim().toList()
    }
//    println(data.first())

    val gamma = data.first().mapIndexed {i: Int, _ -> data.map { it[i] }.mostCommon() }
    val epsilon = gamma.map {
        when (it){
            '0' -> '1'
            '1' -> '0'
            else -> throw RuntimeException("Invalid $it")
    } }

    val gammaInt = Integer.parseInt(gamma.joinToString(""), 2)
    val epsInt = Integer.parseInt(epsilon.joinToString(""), 2)
    println("Part1 answer is ${ gamma } ${ epsilon } ${gammaInt * epsInt}")
}

fun <T> List<T>.mostCommon(): T {
    val counter = mutableMapOf<T, Int>()
    var maxCount = -1
    var key: T? = null

    this.forEach { s ->
        if (s in counter) {
            counter[s] = counter[s]!! + 1

            if (counter[s]!! > maxCount) {
                maxCount = counter[s]!!
                key = s
            }
        } else {
            counter[s] = 1
            if (counter[s]!! > maxCount) {
                maxCount = counter[s]!!
                key = s
            }
        }
    }

    return key!!
}

