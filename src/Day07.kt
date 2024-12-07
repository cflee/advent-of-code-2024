import kotlin.math.*

fun main() {
    fun parse(input: List<String>): List<Pair<Long, List<Long>>> {
        return input.map { line ->
            val s1 = line.split(": ")
            s1[0].toLong() to s1[1].split(" ").map { it.toLong() }
        }
    }
    fun part1(input: List<String>): Long {
        val equations = parse(input)
        fun calc(nums: List<Long>, target: Long, acc: Long, index: Int): Boolean {
            if (index >= nums.size) return acc == target
            if (acc < 0 || acc > target) return false
            return calc(nums, target, acc + nums[index], index + 1)
            || calc(nums, target, acc * nums[index], index + 1)
        }
        return equations.map { equation ->
            if (calc(equation.second, equation.first, 0L, 0)) {
                equation.first
            } else {
                0L
            }
        }.sum()
    }

    fun part2(input: List<String>): Long {
        val equations = parse(input)
        fun calc(nums: List<Long>, target: Long, acc: Long, index: Int): Boolean {
            if (index >= nums.size) return acc == target
            if (acc < 0 || acc > target) return false
            return calc(nums, target, acc + nums[index], index + 1)
            || calc(nums, target, acc * nums[index], index + 1)
            || calc(nums, target, (acc.toString() + nums[index].toString()).toLong(), index + 1)
        }
        return equations.map { equation ->
            if (calc(equation.second, equation.first, 0L, 0)) {
                equation.first
            } else {
                0L
            }
        }.sum()
    }

    val testInput = readInput("Day07_test")
    val input = readInput("Day07")

    part1(testInput).println()
    part1(input).println()

    part2(testInput).println()
    part2(input).println()
}
