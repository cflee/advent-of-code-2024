import kotlin.math.*

fun main() {
    fun part1(input: List<String>): Int {
        val left = mutableListOf<Int>()
        val right = mutableListOf<Int>()
        input.forEach { line ->
            val parts = line.split("   ")
            left.add(parts[0].toInt())
            right.add(parts[1].toInt())
        }
        left.sort()
        right.sort()
        var answer = 0
        left.forEachIndexed { index, l ->
            answer += abs(right[index] - l)
        }
        return answer
    }

    fun part2(input: List<String>): Int {
        val left = mutableListOf<Int>()
        val right = mutableListOf<Int>()
        input.forEach { line ->
            val parts = line.split("   ")
            left.add(parts[0].toInt())
            right.add(parts[1].toInt())
        }
        left.sort()
        val rightFreq = right.asSequence().groupingBy { it }.eachCount()
        var answer = 0
        left.forEachIndexed { index, l ->
            answer += l * rightFreq.getOrDefault(l, 0)
        }
        return answer
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day01_test")
    part1(testInput).println()
    part2(testInput).println()

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
