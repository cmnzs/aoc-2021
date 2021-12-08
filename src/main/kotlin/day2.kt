import java.io.File
import java.lang.Exception


fun main() {
    val source = File("src/main/resources/day2.txt").readLines()
//    val source = """
//        forward 5
//        down 5
//        forward 8
//        up 3
//        down 8
//        forward 2
//    """.trimIndent().split("\n")
    val data = source.map { line ->
        val split = line.split(" ")
        split.first() to split.last().toInt()
    }

    var h = 0
    var d = 0
    data.forEach { (instruction, delta) ->
        when (instruction) {
            "forward" -> h += delta
            "down" -> d += delta
            "up" -> d -= delta
            else -> throw Exception("$instruction is not recognized")
        }
    }
    println("Part1 answer is ${h * d }")

    h = 0
    d = 0
    var aim = 0

    data.forEach { (instruction, delta) ->
        when (instruction) {
            "forward" -> {
                h += delta
                d += aim * delta
            }
            "down" -> {
                aim += delta
            }
            "up" -> {
                aim -= delta
            }
            else -> throw Exception("$instruction is not recognized")
        }
    }
    println("Part2 answer is ${h * d }")


}

