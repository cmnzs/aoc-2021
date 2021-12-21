package day12

import java.io.File

fun main() {
    val source = File("src/main/resources/day12.sample.txt").readLines().map { it.split("-") }
        .also { println(it) }
    val adjacencyMatrix = mutableMapOf<String, MutableMap<String, Int>>()

    source.forEach { edge ->
        val n1 = edge.first()
        val n2 = edge.last()

        adjacencyMatrix.addEdge(n1, n2)
    }

    val paths = mutableListOf<MutableList<String>>()
    val queue = ArrayDeque<String>()
    val visited = mutableSetOf<String>()

    fun neighbours(k: String): List<String> {
        return if (k == "end") {
            emptyList()
        } else {
            adjacencyMatrix[k]!!.keys.toList()
        }
    }

    queue.add("start")

    while (queue.isNotEmpty()) {
        val s = queue.removeFirst()

        val connected = adjacencyMatrix[s]!!.keys

        paths.add(mutableListOf())
    }


    println(adjacencyMatrix)
}


fun MutableMap<String, MutableMap<String,Int>>.addEdge(k1: String, k2: String) {
    if (k1 in this) {
        this[k1]!![k2] = 1
    } else {
        this[k1] = mutableMapOf()
        this[k1]!![k2] = 1
    }

    if (k2 in this) {
        this[k2]!![k1] = 1
    } else {
        this[k2] = mutableMapOf()
        this[k2]!![k1] = 1
    }
}