package day12

import java.io.File


fun main() {
    val source = File("src/main/resources/day12.txt").readLines().map { it.split("-") }.map { it.first() to it.last() }
        .also { println(it) }

    val adjacencyList = (source.map { it.second to it.first  } + source).groupBy({it.first}, {it.second})

    val g = Graph(adjacencyList)
    g.findPaths().onEach { println(it.joinToString(",")) }.also { println("NumPaths: ${it.count()}")}
}


class Graph(val adjacencyList: Map<String, List<String>>) {

    val start = "start"
    val end = "end"

    val pathList = mutableListOf<List<String>>()

    fun findPaths(): MutableList<List<String>> {
        val queue = ArrayDeque<List<String>>()
        pathList.clear()

        queue.add(listOf("start"))

        while (queue.isNotEmpty()) {
            val currentPath = queue.removeFirst()

            if (currentPath.last() == end) {
                pathList.add(currentPath)
            } else {
                val visited = currentPath.toList().filter { it.toUpperCase() != it }.groupBy( { it } ).mapValues { it.value.size }
                println("visited $visited")
                val connected = adjacencyList[currentPath.last()]!!

                val validConnected = connected
                .filterNot {
                    it == start
                }.filter {
                        (it !in visited) || (it in visited && visited.none { it.value == 2 })
                }

                validConnected.map { currentPath + it }.forEach { queue.add(it) }

            }
        }
        return pathList
    }
}