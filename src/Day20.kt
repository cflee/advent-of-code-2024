import java.util.*

import kotlin.math.*

fun main() {
    fun part1(input: List<String>): Long {
        val grid = input.map { it.toCharArray() }

        var startR = 0
        var startC = 0
        for (r in grid.indices) {
            for (c in 0..<grid[r].size) {
                if (input[r][c] == 'S') {
                    startR = r
                    startC = c
                    break
                }
            }
        }

        val DIRS = arrayOf(intArrayOf(-1, 0), intArrayOf(0, 1), intArrayOf(1, 0), intArrayOf(0, -1))
        val dist = Array(grid.size) { IntArray(grid[0].size) { -1 } }
        dist[startR][startC] = 0
        val queue = ArrayDeque<Triple<Int, Int, Int>>()
        queue.add(Triple(startR, startC, 0))
        var answer = 0L
        while (queue.isNotEmpty()) {
            val (r, c, d) = queue.removeFirst()
            DIRS.forEach { dir ->
                val nextR = r + dir[0]
                val nextC = c + dir[1]
                if (
                    nextR in 0..<grid.size && nextC in 0..<grid[0].size
                    && grid[nextR][nextC] != '#' && dist[nextR][nextC] == -1
                ) {
                    queue.add(Triple(nextR, nextC, d + 1))
                    dist[nextR][nextC] = d + 1
                }
                val teleportR = r + dir[0] * 2
                val teleportC = c + dir[1] * 2
                if (
                    teleportR in 0..<grid.size && teleportC in 0..<grid[0].size
                    && grid[teleportR][teleportC] != '#' && dist[teleportR][teleportC] != -1
                ) {
                    val saving = d - dist[teleportR][teleportC] - 2
                    if (saving >= 100) {
                        answer++
                    }
                }
            }
        }
        return answer
    }

    fun part2(input: List<String>): Long {
        val grid = input.map { it.toCharArray() }

        var startR = 0
        var startC = 0
        for (r in grid.indices) {
            for (c in 0..<grid[r].size) {
                if (input[r][c] == 'S') {
                    startR = r
                    startC = c
                    break
                }
            }
        }

        val DIRS = arrayOf(intArrayOf(-1, 0), intArrayOf(0, 1), intArrayOf(1, 0), intArrayOf(0, -1))
        val dist = Array(grid.size) { IntArray(grid[0].size) { -1 } }
        dist[startR][startC] = 0
        val queue = ArrayDeque<Triple<Int, Int, Int>>()
        queue.add(Triple(startR, startC, 0))
        var answer = 0L
        while (queue.isNotEmpty()) {
            val (r, c, d) = queue.removeFirst()
            DIRS.forEach { dir ->
                val nextR = r + dir[0]
                val nextC = c + dir[1]
                if (
                    nextR in 0..<grid.size && nextC in 0..<grid[0].size
                    && grid[nextR][nextC] != '#' && dist[nextR][nextC] == -1
                ) {
                    queue.add(Triple(nextR, nextC, d + 1))
                    dist[nextR][nextC] = d + 1
                }
            }
            for (teleportR in (r - 20)..(r + 20)) {
                for (teleportC in (c - 20)..(c + 20)) {
                    val teleportLen = abs(r - teleportR) + abs(c - teleportC)
                    if (teleportLen > 20) {
                        continue
                    }
                    if (
                        teleportR in 0..<grid.size && teleportC in 0..<grid[0].size
                        && grid[teleportR][teleportC] != '#' && dist[teleportR][teleportC] != -1
                    ) {
                        val saving = d - dist[teleportR][teleportC] - teleportLen
                        if (saving >= 100) {
                            answer++
                        }
                    }
                }
            }
        }
        return answer
    }

    val testInput = readInput("Day20_test")
    val input = readInput("Day20")

    part1(testInput).println()
    part1(input).println()

    part2(testInput).println()
    part2(input).println()
}
