import java.io.File


fun main() {
    val data = File("src/main/resources/day1.txt").readLines().map { it.toInt() }
    var count = 0
    for (i in 1 until data.size) {
        if (data[i] > data[i-1]) {
            count++
        }
    }
    println("Part1 answer is $count")

    // part 2
    val x = data.mapIndexed { index: Int, value: Int ->
        if (index >= (data.size - 2) ) {
            emptyList()
        }
        else {
            listOf(data[index], data[index+1], data[index+2])
        }
    }.map { it.sum() }
    count = 0

    for (i in 1 until x.size) {
        if (x[i] > x[i-1]) {
            count++
        }
    }

    println("Part2 answer is ${count}")
}

