package day10

import java.io.File

val openingBrackets = mapOf(
    '(' to ')',
    '{' to '}',
    '[' to ']',
    '<' to '>',
)

val closingBrackets = mapOf(
    ')' to '(',
    '}' to '{',
    ']' to '[',
    '>' to '<'
)

val score = mapOf(
    ')' to 3,
    ']' to 57,
    '}' to 1197,
    '>' to 25137
)


fun main() {

    val source = File("src/main/resources/day10.txt").readLines()

    source.map { it.toList() }.map { l -> processLine(l) }
        .filter { it.s == LineStatus.CORRUPT }.sumOf { score[it.errorChar]!! }
        .also { println("Part 1: $it") }

    source.map { it.toList() }.map { l -> processLine(l) }
        .filter { it.s == LineStatus.INCOMPLETE }
        .map { scoreRemaining(it.remainingLine!!) to it.remainingLine }
        .onEach { println("Part 2: $it") }
        .map { it.first }
        .sorted().let {
            it[it.size/2]
        }.also { println(it) }
}

fun scoreRemaining(l: List<Char>): Long {
    var result = 0L
    val scores = mapOf(
        ')' to 1,
        ']' to 2,
        '}' to 3,
        '>' to 4
    )

    l.forEach { i ->
        result = result * 5 + scores[i]!!
    }

    return result
}

data class LineResult(val s: LineStatus, val errorChar: Char?, val remainingLine: List<Char>?)
enum class LineStatus {
    OK,
    CORRUPT,
    INCOMPLETE
}

fun processLine(s: List<Char>): LineResult {
    val stack = ArrayDeque<Char>()
    s.forEach { c ->
        if (c in openingBrackets) {
            stack.add(c)
        } else {
            // it's a closing bracket
            if (stack.last() == closingBrackets[c]) {
                // it matches
                stack.removeLast()
            } else {
                return LineResult(LineStatus.CORRUPT, c, null)
            }
        }
    }
    if (stack.isNotEmpty()) {

        return LineResult(LineStatus.INCOMPLETE, null, stack.map { openingBrackets[it]!! }.reversed())
    }
    return LineResult(LineStatus.OK, null, null)
}
