import java.util.*

import kotlin.math.*

fun main() {
    fun part1(input: List<String>): Long {
        val n = input.size
        val m = input[0].length
        val data = input.map { line ->
            val lineList = mutableListOf<Int>()
            line.forEach { lineList.add(it - '0') }
            lineList
        }
        val DIRS = arrayOf(intArrayOf(0, 1), intArrayOf(0, -1), intArrayOf(1, 0), intArrayOf(-1, 0))
        var answer = 0L
        for (i in 0..<n) {
            for (j in 0..<m) {
                if (data[i][j] != 0) continue
                val trailTops = mutableSetOf<Int>()
                val queue = ArrayDeque<Pair<Int, Int>>()
                queue.addLast(i to j)
                while (queue.isNotEmpty()) {
                    val (r, c) = queue.removeFirst()
                    if (data[r][c] == 9) {
                        trailTops.add(r * m + c)
                        continue
                    }
                    DIRS.forEach { dir ->
                        val nextR = r + dir[0]
                        val nextC = c + dir[1]
                        if (nextR in 0..<n && nextC in 0..<m && data[nextR][nextC] == data[r][c] + 1) {
                            queue.add(nextR to nextC)
                        }
                    }
                }
                answer += trailTops.size
            }
        }
        return answer
    }

    fun part2(input: List<String>): Long {
        val n = input.size
        val m = input[0].length
        val data = input.map { line ->
            val lineList = mutableListOf<Int>()
            line.forEach { lineList.add(it - '0') }
            lineList
        }
        val DIRS = arrayOf(intArrayOf(0, 1), intArrayOf(0, -1), intArrayOf(1, 0), intArrayOf(-1, 0))
        var answer = 0L
        for (i in 0..<n) {
            for (j in 0..<m) {
                if (data[i][j] != 0) continue
                val queue = ArrayDeque<Pair<Int, Int>>()
                queue.addLast(i to j)
                while (queue.isNotEmpty()) {
                    val (r, c) = queue.removeFirst()
                    if (data[r][c] == 9) {
                        answer++
                        continue
                    }
                    DIRS.forEach { dir ->
                        val nextR = r + dir[0]
                        val nextC = c + dir[1]
                        if (nextR in 0..<n && nextC in 0..<m && data[nextR][nextC] == data[r][c] + 1) {
                            queue.add(nextR to nextC)
                        }
                    }
                }
            }
        }
        return answer
    }

    val testInput = readInput("Day10_test")
    val input = readInput("Day10")

    part1(testInput).println()
    part1(input).println()

    part2(testInput).println()
    part2(input).println()
}
