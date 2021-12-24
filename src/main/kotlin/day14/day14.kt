package day14
import java.io.File

fun main() {
    val source = File("src/main/resources/day14.txt").readLines()

    val template = source.first()
    val insertionRules = source.drop(2).map { it.split(" -> ").let { it.first() to it.last() } }.toMap()
    println(insertionRules)

    var tt = template
    println(tt)
    val dict = mutableMapOf<String, String>()
    (1..10).forEach { it ->
//        println("step $it - $tt")
        val temp = step(tt, insertionRules)
        dict.put(tt, temp)
        tt = temp
    }
    println(tt.groupBy { it }.mapValues { it.value.size }.also{println(it)}.values.sorted().let { it.last() - it.first() })

    println("Part2:")

    val pairs = generate(template, insertionRules, 40)

    val counts = mutableMapOf<String, Long>()

    pairs.forEach { (k, v) ->
        val letter = k.first().toString()
        if (letter in counts) {
            counts[letter] = counts[letter]!! + v
        } else {
            counts[letter] = v
        }
    }
    val lastLetter = template.last().toString()
    if (lastLetter in counts) {
        counts[lastLetter] = counts[lastLetter]!! + 1
    } else {
        counts[lastLetter] = 1
    }
        //.values.sorted().let { it.last() - it.first() }
    counts.also{ println("CharMap: $it") }.values.sorted().let { it.last() - it.first() }.also { println("P2: $it") }

}

fun generate(ss: String, rules: Map<String, String>, steps: Int): Map<String, Long> {

    fun step(charMap: Map<String, Long>): Map<String, Long> {
        val mutableMap = mutableMapOf<String, Long>()

        for ((pair, count) in charMap.entries) {
            val insert = rules[pair]!!
            val p1 = pair.first() + insert
            val p2 = insert + pair.last()
            if (p1 in mutableMap) {
                mutableMap[p1] = count + mutableMap[p1]!!
            } else {
                mutableMap[p1] = count
            }
            if (p2 in mutableMap) {
                mutableMap[p2] = count + mutableMap[p2]!!
            } else {
                mutableMap[p2] = count
            }

        }
        return mutableMap
    }

    var tt = ss.zipWithNext().map { "${it.first}${it.second}" }.groupBy { it }.mapValues { it.value.size.toLong() }
    (1..steps).forEach {
        tt = step(tt)
    }

    return tt
}



fun step(tt: String, insertionRules: Map<String, String>): String {
    val ns = tt.zipWithNext().map {
        val ss = "${it.first}${it.second}"
        if (ss in insertionRules) {
            val x = insertionRules[ss]!!
            "${it.first}${x}"
        } else {
            "${it.first}"
        }

    }
    return ns.joinToString("") + tt.last()
}