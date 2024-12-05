import kotlin.math.*

fun main() {
    fun part1(input: List<String>): Int {
        val rules = mutableListOf<List<Int>>()
        val updates = mutableListOf<List<Int>>()

        var lineIndex = 0
        while (lineIndex < input.size && input[lineIndex].length != 0) {
            rules.add(input[lineIndex].split("|").map { it.toInt() })
            lineIndex++
        }
        lineIndex++
        while (lineIndex < input.size && input[lineIndex].length != 0) {
            updates.add(input[lineIndex].split(",").map { it.toInt() })
            lineIndex++
        }

        var answer = 0
        updates.forEach { update ->
            val pages = mutableMapOf<Int, Int>()
            update.forEachIndexed { index, u ->
                pages[u] = index
            }
            var ok = true
            for (i in rules.indices) {
                val (x, y) = rules[i]
                if (pages.containsKey(x) && pages.containsKey(y) && pages[x]!! > pages[y]!!) {
                    ok = false
                    break
                }
            }
            if (ok) {
                answer += update[(update.size / 2)].toInt()
            }
        }
        return answer
    }

    fun part2(input: List<String>): Int {
        val rules = mutableListOf<List<Int>>()
        val updates = mutableListOf<List<Int>>()

        var lineIndex = 0
        while (lineIndex < input.size && input[lineIndex].length != 0) {
            val line = input[lineIndex].split("|").map { it.toInt() }
            rules.add(line)
            lineIndex++
        }
        lineIndex++
        while (lineIndex < input.size && input[lineIndex].length != 0) {
            updates.add(input[lineIndex].split(",").map { it.toInt() })
            lineIndex++
        }

        var answer = 0
        updates.forEach { update ->
            val updatePages = update.toSet()

            val adjs = mutableMapOf<Int, MutableList<Int>>()
            val inDeg = mutableMapOf<Int, Int>()
            rules.forEach { line ->
                if (updatePages.contains(line[0]) && updatePages.contains(line[1])) {
                    adjs.getOrPut(line[0]) { mutableListOf() }.add(line[1])
                    adjs.getOrPut(line[1]) { mutableListOf() }
                    inDeg.getOrPut(line[0]) { 0 }
                    inDeg[line[1]] = (inDeg[line[1]] ?: 0) + 1
                }
            }

            val sorted = mutableListOf<Int>()
            val queue = ArrayDeque<Int>()
            val queued = mutableSetOf<Int>()
            inDeg.forEach {
                if (it.value == 0) {
                    queue.addLast(it.key)
                    queued.add(it.key)
                }
            }
            while (queue.isNotEmpty()) {
                val cur = queue.removeFirst()
                sorted.add(cur)
                adjs[cur]!!.forEach { adj ->
                    if (!queued.contains(adj)) {
                        inDeg[adj] = inDeg[adj]!! - 1
                        if (inDeg[adj]!! == 0) {
                            queue.addLast(adj)
                            queued.add(adj)
                        }
                    }
                }
            }
            val sortedHash = mutableMapOf<Int, Int>()
            sorted.forEachIndexed { index, s ->
                sortedHash[s] = index
            }

            val updateIndices = update.map {
                sortedHash[it]!!
            }
            val updateIndicesSorted = updateIndices.sorted()
            if (updateIndices != updateIndicesSorted) {
                answer += sorted[updateIndicesSorted[updateIndicesSorted.size / 2]]
            }
        }
        return answer
    }

    val testInput = readInput("Day05_test")
    val input = readInput("Day05")

    part1(testInput).println()
    part1(input).println()

    part2(testInput).println()
    part2(input).println()
}
