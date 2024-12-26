import java.util.*

import kotlin.math.*

fun main() {
    fun part1(input: List<String>): Long {
        val DIRS = arrayOf(intArrayOf(-1, 0), intArrayOf(0, 1), intArrayOf(1, 0), intArrayOf(0, -1))
        val totalR = input.size
        val totalC = input[0].length

        var startR = 0
        var startC = 0
        outerLoop@ for (r in 0..<totalR) {
            for (c in 0..<totalC) {
                if (input[r][c] == 'S') {
                    startR = r
                    startC = c
                    break
                }
            }
        }
        
        val dist = Array(totalR) { Array(totalC) { LongArray(4) { Long.MAX_VALUE } } }
        data class Record(val score: Long, val row: Int, val col: Int, val dir: Int)
        val queue = PriorityQueue<Record>(compareBy({ it.score }))
        queue.add(Record(score = 0, row = startR, col = startC, dir = 1))
        dist[startR][startC][1] = 0
        while (queue.isNotEmpty()) {
            val cur = queue.remove()
            if (input[cur.row][cur.col] == 'E') {
                return cur.score
            }
            val forwardR = cur.row + DIRS[cur.dir][0]
            val forwardC = cur.col + DIRS[cur.dir][1]
            if (input[forwardR][forwardC] != '#') {
                if (cur.score + 1 < dist[forwardR][forwardC][cur.dir]) {
                    queue.add(Record(score = cur.score + 1, row = forwardR, col = forwardC, dir = cur.dir))
                    dist[forwardR][forwardC][cur.dir] = cur.score + 1
                }
            }
            val cwDir = (cur.dir + 1) % 4
            if (cur.score + 1000 < dist[cur.row][cur.col][cwDir]) {
                queue.add(Record(score = cur.score + 1000, row = cur.row, col = cur.col, dir = cwDir))
                dist[cur.row][cur.col][cwDir] = cur.score + 1000
            }
            val ccwDir = (cur.dir + 3) % 4
            if (cur.score + 1000 < dist[cur.row][cur.col][ccwDir]) {
                queue.add(Record(score = cur.score + 1000, row = cur.row, col = cur.col, dir = ccwDir))
                dist[cur.row][cur.col][ccwDir] = cur.score + 1000
            }
        }
        return 0L
    }

    fun part2(input: List<String>): Long {
        val DIRS = arrayOf(intArrayOf(-1, 0), intArrayOf(0, 1), intArrayOf(1, 0), intArrayOf(0, -1))
        val totalR = input.size
        val totalC = input[0].length

        var startR = 0
        var startC = 0
        outerLoop@ for (r in 0..<totalR) {
            for (c in 0..<totalC) {
                if (input[r][c] == 'S') {
                    startR = r
                    startC = c
                    break
                }
            }
        }
        
        val dist = Array(totalR) { Array(totalC) { LongArray(4) { Long.MAX_VALUE } } }
        val parents = Array(totalR) { Array(totalC) { Array(4) { mutableListOf<Triple<Int, Int, Int>>() } } }
        data class Record(val score: Long, val row: Int, val col: Int, val dir: Int)
        val queue = PriorityQueue<Record>(compareBy({ it.score }))
        queue.add(Record(score = 0, row = startR, col = startC, dir = 1))
        dist[startR][startC][1] = 0
        var shortestPathLength = -1L
        val endStates = mutableSetOf<Triple<Int, Int, Int>>()
        while (queue.isNotEmpty()) {
            val cur = queue.remove()
            if (shortestPathLength != -1L && cur.score > shortestPathLength) {
                break
            }
            if (input[cur.row][cur.col] == 'E') {
                shortestPathLength = cur.score
                endStates.add(Triple(cur.row, cur.col, cur.dir))
                continue
            }
            val forwardR = cur.row + DIRS[cur.dir][0]
            val forwardC = cur.col + DIRS[cur.dir][1]
            if (input[forwardR][forwardC] != '#') {
                if (cur.score + 1 <= dist[forwardR][forwardC][cur.dir]) {
                    if (cur.score + 1 < dist[forwardR][forwardC][cur.dir]) {
                        queue.add(Record(score = cur.score + 1, row = forwardR, col = forwardC, dir = cur.dir))
                        dist[forwardR][forwardC][cur.dir] = cur.score + 1
                        parents[forwardR][forwardC][cur.dir].clear()
                    }
                    parents[forwardR][forwardC][cur.dir].add(Triple(cur.row, cur.col, cur.dir))
                }
            }
            val cwDir = (cur.dir + 1) % 4
            if (cur.score + 1000 <= dist[cur.row][cur.col][cwDir]) {
                if (cur.score + 1000 < dist[cur.row][cur.col][cwDir]) {
                    queue.add(Record(score = cur.score + 1000, row = cur.row, col = cur.col, dir = cwDir))
                    dist[cur.row][cur.col][cwDir] = cur.score + 1000
                    parents[cur.row][cur.col][cwDir].clear()
                }
                parents[cur.row][cur.col][cwDir].add(Triple(cur.row, cur.col, cur.dir))
            }
            val ccwDir = (cur.dir + 3) % 4
            if (cur.score + 1000 <= dist[cur.row][cur.col][ccwDir]) {
                if (cur.score + 1000 < dist[cur.row][cur.col][ccwDir]) {
                    queue.add(Record(score = cur.score + 1000, row = cur.row, col = cur.col, dir = ccwDir))
                    dist[cur.row][cur.col][ccwDir] = cur.score + 1000
                    parents[cur.row][cur.col][ccwDir].clear()
                }
                parents[cur.row][cur.col][ccwDir].add(Triple(cur.row, cur.col, cur.dir))
            }
        }

        val bestPathTiles = mutableSetOf<Pair<Int, Int>>()
        val bfsQueue = ArrayDeque<Triple<Int, Int, Int>>()
        bfsQueue.addAll(endStates)
        while (bfsQueue.isNotEmpty()) {
            val (row, col, dir) = bfsQueue.remove()
            bestPathTiles.add(Pair(row, col))
            parents[row][col][dir].forEach {
                bfsQueue.add(it)
            }
        }
        return bestPathTiles.size.toLong()
    }

    val testInput = readInput("Day16_test")
    val input = readInput("Day16")

    part1(testInput).println()
    part1(input).println()

    part2(testInput).println()
    part2(input).println()
}
