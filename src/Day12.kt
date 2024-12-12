import java.util.*

import kotlin.math.*

fun main() {
    fun part1(input: List<String>): Long {
        val DIRS = arrayOf(intArrayOf(0, 1), intArrayOf(0, -1), intArrayOf(1, 0), intArrayOf(-1, 0))
        val n = input.size
        val m = input[0].length
        fun isValid(row: Int, col: Int) = row in 0..<n && col in 0..<m
        val visited = mutableSetOf<Int>()
        var answer = 0L
        for (startR in 0..<n) {
            for (startC in 0..<m) {
                if (visited.contains(startR * m + startC)) {
                    continue
                }
                val target = input[startR][startC]
                var area = 0
                var perimeter = 0
                val queue = ArrayDeque<Pair<Int, Int>>()
                queue.addLast(startR to startC)
                visited.add(startR * m + startC)
                while (queue.isNotEmpty()) {
                    val (curR, curC) = queue.removeFirst()
                    area++
                    DIRS.forEach { dir ->
                        val nextR = curR + dir[0]
                        val nextC = curC + dir[1]
                        if (isValid(nextR, nextC)) {
                            if (input[nextR][nextC] != target) {
                                perimeter++
                            } else {
                                if (!visited.contains(nextR * m + nextC)) {
                                    queue.addLast(nextR to nextC)
                                    visited.add(nextR * m + nextC)
                                }
                            }
                        } else {
                            perimeter++
                        }
                    }
                }
                answer += area.toLong() * perimeter
            }
        }
        return answer
    }

    fun part2(input: List<String>): Long {
        data class Border(val row: Int, val col: Int, val side: Int)
        val DIRS = arrayOf(intArrayOf(-1, 0), intArrayOf(0, -1), intArrayOf(1, 0), intArrayOf(0, 1))
        val n = input.size
        val m = input[0].length
        fun isValid(row: Int, col: Int) = row in 0..<n && col in 0..<m
        val visited = mutableSetOf<Int>()
        var answer = 0L
        for (startR in 0..<n) {
            for (startC in 0..<m) {
                if (visited.contains(startR * m + startC)) {
                    continue
                }
                val target = input[startR][startC]
                var area = 0
                var borders = mutableListOf<Border>()
                val queue = ArrayDeque<Pair<Int, Int>>()
                queue.addLast(startR to startC)
                visited.add(startR * m + startC)
                while (queue.isNotEmpty()) {
                    val (curR, curC) = queue.removeFirst()
                    area++
                    DIRS.forEachIndexed { dirIndex, dir ->
                        val nextR = curR + dir[0]
                        val nextC = curC + dir[1]
                        if (!isValid(nextR, nextC) || input[nextR][nextC] != target) {
                            // invalid, or valid && next one is not target
                            when (dirIndex) {
                                0 -> borders.add(Border(curR, curC, 0))
                                1 -> borders.add(Border(curR, curC, 1))
                                2 -> borders.add(Border(curR + 1, curC, 2))
                                else -> borders.add(Border(curR, curC + 1, 3))
                            }
                        } else {
                            // valid && next one is target
                            if (!visited.contains(nextR * m + nextC)) {
                                queue.addLast(nextR to nextC)
                                visited.add(nextR * m + nextC)
                            }
                        }
                    }
                }
                val parent = IntArray(borders.size) { it }
                fun find(id: Int): Int {
                    var cur = parent[id]
                    if (parent[cur] != cur) {
                        parent[id] = find(cur)
                    }
                    return parent[id]
                }
                fun union(id1: Int, id2: Int) {
                    parent[find(id2)] = find(id1)
                }
                val borderLookup = mutableMapOf<Border, Int>()
                borders.forEachIndexed { borderIndex, border ->
                    borderLookup[border] = borderIndex
                }
                borders.forEachIndexed { borderIndex, border ->
                    if (border.side == 0 || border.side == 2) {
                        // horizontal
                        val left = Border(border.row, border.col - 1, border.side)
                        val right = Border(border.row, border.col + 1, border.side)
                        if (borderLookup.containsKey(left)) {
                            union(borderIndex, borderLookup[left]!!)
                        }
                        if (borderLookup.containsKey(right)) {
                            union(borderIndex, borderLookup[right]!!)
                        }
                    } else {
                        // vertical
                        val up = Border(border.row - 1, border.col, border.side)
                        val down = Border(border.row + 1, border.col, border.side)
                        if (borderLookup.containsKey(up)) {
                            union(borderIndex, borderLookup[up]!!)
                        }
                        if (borderLookup.containsKey(down)) {
                            union(borderIndex, borderLookup[down]!!)
                        }
                    }
                }
                for (i in borders.indices) find(i)
                val sides = parent.toSet().size
                answer += area.toLong() * sides
            }
        }
        return answer
    }

    val testInput = readInput("Day12_test")
    val input = readInput("Day12")

    part1(testInput).println()
    part1(input).println()

    part2(testInput).println()
    part2(input).println()
}
