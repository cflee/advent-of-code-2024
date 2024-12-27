import java.util.*

import kotlin.math.*

fun main() {
    fun part1(input: List<String>): Int {
        val pairs = input.map { it.split("-") }
        val adjs = mutableMapOf<String, MutableList<String>>()
        val startsWithT = mutableSetOf<String>()
        pairs.forEach { pair ->
            adjs.getOrPut(pair[0]) { mutableListOf() }.add(pair[1])
            adjs.getOrPut(pair[1]) { mutableListOf() }.add(pair[0])
            if (pair[0].startsWith("t")) startsWithT.add(pair[0])
            if (pair[1].startsWith("t")) startsWithT.add(pair[1])
        }
        val answer = mutableSetOf<Set<String>>()
        startsWithT.forEach { start ->
            val visited = mutableSetOf<String>()
            fun recurse(start: String, cur: String, len: Int) {
                val adj = adjs.get(cur)!!
                adj.forEach { neighbour ->
                    if (len == 3) {
                        // looking for the start
                        if (neighbour == start) {
                            answer.add(visited.toSet())
                        }
                    } else if (!visited.contains(neighbour)) {
                        visited.add(neighbour)
                        recurse(start, neighbour, len + 1)
                        visited.remove(neighbour)
                    }
                }
            }
            visited.add(start)
            recurse(start, start, 1)
        }
        return answer.size
    }

    fun part2(input: List<String>): String {
        val adjs = mutableMapOf<String, MutableSet<String>>()
        input.map { it.split("-") }.forEach { (v, u) ->
            adjs.getOrPut(u) { mutableSetOf() }.add(v)
            adjs.getOrPut(v) { mutableSetOf() }.add(u)
        }

        var bestSize = 0
        var bestAnswer = setOf<String>()
        val nodes = adjs.keys.toList()
        val visited = mutableSetOf<String>()
        fun recurse(cur: Int) {
            if (cur >= nodes.size) {
                return
            }
            if (visited.size > bestSize) {
                bestSize = visited.size
                bestAnswer = visited.toSet()
            }
            if (adjs[nodes[cur]]!!.containsAll(visited)) {
                visited.add(nodes[cur])
                recurse(cur + 1)
                visited.remove(nodes[cur])
            }
            recurse(cur + 1)
        }
        recurse(0)
        return bestAnswer.sorted().joinToString(",")
    }

    val testInput = readInput("Day23_test")
    val input = readInput("Day23")

    part1(testInput).println()
    part1(input).println()

    part2(testInput).println()
    part2(input).println()
}
