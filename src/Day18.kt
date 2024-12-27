import java.util.*

import kotlin.math.*

fun main() {
    fun part1(input: List<String>, maxCoord: Int, byteCount: Int): Int {
        val DIRS = arrayOf(intArrayOf(-1, 0), intArrayOf(0, 1), intArrayOf(1, 0), intArrayOf(0, -1))

        val incoming = mutableListOf<Pair<Int, Int>>()
        input.forEach { line ->
            val parts = line.split(",").map { it.toInt() }
            incoming.add(Pair(parts[0], parts[1]))
        }
        val grid = Array(maxCoord + 1) { CharArray(maxCoord + 1) { ' ' } }
        repeat(byteCount) {
            val falling = incoming[it]
            grid[falling.second][falling.first] = '#'
        }

        val enqueued = Array(maxCoord + 1) { BooleanArray(maxCoord + 1) }
        val queue = PriorityQueue<Triple<Int, Int, Int>>(compareBy ({ it.first }))
        queue.add(Triple(0, 0, 0))
        enqueued[0][0] = true
        while (queue.isNotEmpty()) {
            val (dist, x, y) = queue.remove()
            if (x == maxCoord && y == maxCoord) {
                return dist
            }
            DIRS.forEach { dir ->
                val nextX = x + dir[0]
                val nextY = y + dir[1]
                if (
                    nextX in 0..maxCoord && nextY in 0..maxCoord && !enqueued[nextX][nextY] && grid[nextX][nextY] != '#'
                ) {
                    enqueued[nextX][nextY] = true
                    queue.add(Triple(dist + 1, nextX, nextY))
                }
            }
        }
        return 0
    }

    fun shortestPath(grid: Array<CharArray>): Int {
        val DIRS = arrayOf(intArrayOf(-1, 0), intArrayOf(0, 1), intArrayOf(1, 0), intArrayOf(0, -1))
        val enqueued = Array(grid.size) { BooleanArray(grid.size) }
        val queue = PriorityQueue<Triple<Int, Int, Int>>(compareBy ({ it.first }))
        queue.add(Triple(0, 0, 0))
        enqueued[0][0] = true
        while (queue.isNotEmpty()) {
            val (dist, x, y) = queue.remove()
            if (x == grid.size - 1 && y == grid.size - 1) {
                return dist
            }
            DIRS.forEach { dir ->
                val nextX = x + dir[0]
                val nextY = y + dir[1]
                if (
                    nextX in 0..(grid.size - 1) && nextY in 0..(grid.size - 1) 
                    && !enqueued[nextX][nextY] && grid[nextX][nextY] != '#'
                ) {
                    enqueued[nextX][nextY] = true
                    queue.add(Triple(dist + 1, nextX, nextY))
                }
            }
        }
        return -1
    }

    fun part2(input: List<String>, maxCoord: Int, initialByteCount: Int): String {
        val incoming = mutableListOf<Pair<Int, Int>>()
        input.forEach { line ->
            val parts = line.split(",").map { it.toInt() }
            incoming.add(Pair(parts[0], parts[1]))
        }
        val grid = Array(maxCoord + 1) { CharArray(maxCoord + 1) { ' ' } }
        repeat(initialByteCount) {
            val falling = incoming[it]
            grid[falling.second][falling.first] = '#'
        }
        for (i in initialByteCount..<incoming.size) {
            val falling = incoming[i]
            grid[falling.second][falling.first] = '#'
            val result = shortestPath(grid)
            if (result == -1) {
                return "${falling.first},${falling.second}"
            }
        }
        return "?"
    }

    val testInput = readInput("Day18_test")
    val input = readInput("Day18")

    part1(testInput, 6, 12).println()
    part1(input, 70, 1024).println()

    part2(testInput, 6, 0).println()
    part2(input, 70, 1024).println()
}
