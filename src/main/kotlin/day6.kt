import java.io.File

fun main() {
    val source = File("src/main/resources/day6.txt").readLines().first().split(",").map { it.toInt() }

    var arr = LongArray(9)

    source.forEach { arr[it] = arr[it] + 1 }
    
    (1..256).forEach { i ->
        arr = step(arr)
        println("Iter $i count: ${arr.toList().sum()}")
    }

}

fun step(a: LongArray): LongArray {
    val b = LongArray(9)

    // everything that hit 0 -> spawn a new fish at index 10
    b[8] = a[0]
    b[7] = a[8]
    // everything that hit 0 -> reset timer to 6
    b[6] = a[0] + a[7]
    b[5] = a[6]
    b[4] = a[5]
    b[3] = a[4]
    b[2] = a[3]
    b[1] = a[2]
    b[0] = a[1]

    return b
}