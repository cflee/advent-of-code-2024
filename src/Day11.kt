import java.util.*

import kotlin.math.*

fun main() {
    fun part1(input: List<String>): Int {
        var stones = input[0].split(" ")
        repeat(25) {
            val newStones = mutableListOf<String>()
            stones.forEach { stone ->
                if (stone == "0") {
                    newStones.add("1")
                } else if (stone.length % 2 == 0) {
                    newStones.add(stone.substring(0, stone.length / 2))
                    newStones.add(stone.substring(stone.length / 2).toLong().toString())
                } else {
                    newStones.add((stone.toLong() * 2024).toString())
                }
            }
            stones = newStones
        }
        return stones.size
    }

    fun part2(input: List<String>, blinks: Int): Long {
        var stones = input[0].split(" ")
        val memo = mutableMapOf<Pair<String, Int>, Long>()
        fun recurse(num: String, blinks: Int): Long {
            val memoKey = Pair<String, Int>(num, blinks)
            if (memo.containsKey(memoKey)) return memo.get(memoKey)!!
            var result = 0L
            if (blinks == 0) {
                result = 1L
            } else {
                if (num == "0") {
                    result += recurse("1", blinks - 1)
                } else if (num.length % 2 == 0) {
                    result += recurse(num.substring(0, num.length / 2), blinks - 1)
                    result += recurse(num.substring(num.length / 2).toLong().toString(), blinks - 1)
                } else {
                    result += recurse((num.toLong() * 2024).toString(), blinks - 1)
                }
            }
            memo[memoKey] = result
            return result
        }
        var answer = 0L
        stones.forEach { stone ->
            answer += recurse(stone, blinks)
        }
        return answer
    }

    val testInput = readInput("Day11_test")
    val input = readInput("Day11")

    part1(testInput).println()
    part1(input).println()

    part2(testInput, 25).println()
    part2(input, 25).println()

    part2(testInput, 75).println()
    part2(input, 75).println()
}
