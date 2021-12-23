package day14
import java.io.File

fun main() {
    val source = File("src/main/resources/day14.sample.txt").readLines()

    val template = source.first()
    val insertionRules = source.drop(2).map { it.split(" -> ").let { it.first() to it.last() } }.toMap()
    println(insertionRules)

    var tt = template
    println(tt)
    (1..10).forEach {
        println("iteration $it")
        tt = step(tt, insertionRules)
//        println(tt)
    }

    println(tt.groupBy( { it }).mapValues { it.value.size }.values.sorted().let { it.last() - it.first() })
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

