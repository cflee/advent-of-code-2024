import java.util.*

import kotlin.math.*

fun main() {
    fun part1(input: List<String>): Long {
        val keys = mutableListOf<IntArray>()
        val locks = mutableListOf<IntArray>()
        var inputLine = 0
        while (inputLine < input.size) {
            val data = IntArray(5)
            for (col in 0..<5) {
                var count = 0
                for (row in 1..5) {
                    if (input[inputLine + row][col] == '#') count++
                }
                data[col] = count
            }
            if (input[inputLine] == "#####") {
                locks.add(data)
            } else {
                keys.add(data)
            }
            inputLine += 8
        }
        var answer = 0L
        locks.forEach { lock ->
            keys.forEach { key ->
                val sums = IntArray(5)
                for (col in 0..<5) {
                    sums[col] = lock[col] + key[col]
                }
                if (sums.filter { it > 5 }.count() == 0) answer++
            }
        }
        return answer
    }

    fun part2(input: List<String>): Long {
        return 0L
    }

    val testInput = readInput("Day25_test")
    val input = readInput("Day25")

    part1(testInput).println()
    part1(input).println()

    // part2(testInput).println()
    // part2(input).println()
}
