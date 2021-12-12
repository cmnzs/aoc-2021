import java.io.File
import java.util.*


val digitMap = mapOf(
    '0' to "abcefg",
    '1' to "cf",
    '2' to "acdeg",
    '3' to "acdfg",
    '4' to "bcdf",
    '5' to "abdfg",
    '6' to "abdefg",
    '7' to "acf",
    '8' to "abcdefg",
    '9' to "abcdfg"
)

val reversed = digitMap.entries.associateBy({ it.value }) { it.key }

fun main() {
    val source = File("src/main/resources/day8.txt")

    source
        .readLines()
        .map { it.split("|").last().trim().split(" ") }
        .flatMap{ it }
        .groupBy { t -> t.count() }
        .filter { it.key in setOf(2, 3, 4, 7) }
        .flatMap { it.value }
        .count()
        .also { println("Part1: $it") }

    println("HH: ${digitMap['0']!!.toSet().intersect(digitMap['6']!!.toSet()).intersect(digitMap['9']!!.toSet())}")
    source
        .readLines()
        .map { processLine(it) }
        .sum()
        .also { println("Part2: $it") }


}

fun processLine(s: String): Int {
    val x = s.split("|").map{it.trim()}
    val input = x.first()
    val output = x.last()

    val tokens = input.trim().split(" ").map { it.toSortedSet().joinToString("") }

    val originalAlphabet = "abcdefg".toList()

    return originalAlphabet.permutations().map { possibleAlphabet ->
        possibleAlphabet to tokens.map { token ->
            val x = token
                .toList()
                .map { originalAlphabet[possibleAlphabet.indexOf(it)] }
                .joinToString("").toSortedSet().joinToString("")

            reversed[x]
        }
    }.first { pair -> pair.second.all { it != null } } // check for a good match to the alphabet
        .let { pair ->
        val alpha = pair.first

        val tokens = output.trim().split(" ").map { it.toSortedSet().joinToString("") }
        tokens.map {
            val x = it.toList()
            .map { originalAlphabet[alpha.indexOf(it)] }
            .joinToString("").toSortedSet().joinToString("")

            reversed[x]
        }.let {
            it.joinToString("").toInt()
        }
    }

}

// from the internet
fun <V> List<V>.permutations(): List<List<V>> {
    val retVal: MutableList<List<V>> = mutableListOf()

    fun generate(k: Int, list: List<V>) {
        // If only 1 element, just output the array
        if (k == 1) {
            retVal.add(list.toList())
        } else {
            for (i in 0 until k) {
                generate(k - 1, list)
                if (k % 2 == 0) {
                    Collections.swap(list, i, k - 1)
                } else {
                    Collections.swap(list, 0, k - 1)
                }
            }
        }
    }

    generate(this.count(), this.toList())
    return retVal
}