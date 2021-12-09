import java.io.File
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

    val gammaInt = gamma.binaryToInt()
    val epsInt = epsilon.binaryToInt()
    println("Part1 answer is ${ gamma } ${ epsilon } ${gammaInt * epsInt}")


    val oxygenGeneratorRating = getOxygenGeneratorRating(0, data)
    val co2ScrubberRating = getCo2ScrubberRating(0, data)

    println("ox: $oxygenGeneratorRating co2: $co2ScrubberRating")

    val lifeSupportRating: Int = oxygenGeneratorRating.binaryToInt() * co2ScrubberRating.binaryToInt()

    println("Part2 Answer is $lifeSupportRating")
}

fun List<Char>.binaryToInt(): Int {
    return Integer.parseInt(this.joinToString(""), 2)
}

fun getCo2ScrubberRating(bitPosition: Int, sourceData: List<List<Char>>): List<Char> {
    val bitFilter: (List<List<Char>>, Int) -> List<List<Char>> = { filteredData, bitPosition ->
        val countZero = filteredData.count { it[bitPosition] == '0' }
        val countOne = filteredData.count { it[bitPosition] == '1' }

        val expected = if (countZero == countOne) {
            '0'
        } else {
            if (countZero < countOne) {
                '0'
            } else {
                '1'
            }
        }
        filteredData.filter { it[bitPosition] == expected }
    }

    val filterResult = bitFilter(sourceData, bitPosition)
    return if (filterResult.size == 1) {
        filterResult.first()
    } else {
        getCo2ScrubberRating(bitPosition+1, filterResult)
    }
}

fun getOxygenGeneratorRating(bitPosition: Int, sourceData: List<List<Char>>): List<Char> {
    val bitFilter: (List<List<Char>>, Int) -> List<List<Char>> = { filteredData, bitPosition ->
        val countZero = filteredData.count { it[bitPosition] == '0' }
        val countOne = filteredData.count { it[bitPosition] == '1' }

        val expected = if (countZero == countOne) {
            '1'
        } else {
            if (countZero > countOne) {
                '0'
            } else {
                '1'
            }
        }
        filteredData.filter { it[bitPosition] == expected }
    }

    val filterResult = bitFilter(sourceData, bitPosition)
    return if (filterResult.size == 1) {
        filterResult.first()
    } else {
        getOxygenGeneratorRating(bitPosition+1, filterResult)
    }

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

