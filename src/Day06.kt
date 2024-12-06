import kotlin.math.*

fun main() {
    fun part1(input: List<String>): Int {
        val n = input.size
        val m = input[0].length
        fun isValid(row: Int, col: Int) = row in 0..<n && col in 0..<m
        val DIRS = arrayOf(
            intArrayOf(-1, 0),
            intArrayOf(0, 1),
            intArrayOf(1, 0),
            intArrayOf(0, -1),
        )
        var guardRow = 0
        var guardCol = 0
        var guardDir = 0
        val map = Array(n) { BooleanArray(m) }
        input.forEachIndexed { lineIndex, line ->
            line.forEachIndexed { colIndex, c ->
                if (c == '#') map[lineIndex][colIndex] = true
                if (c == '^') {
                    guardRow = lineIndex
                    guardCol = colIndex
                }
            }
        }
        val visited = mutableSetOf<Int>()
        while (isValid(guardRow, guardCol)) {
            visited.add(guardRow * m + guardCol)
            val nextRow = guardRow + DIRS[guardDir][0]
            val nextCol = guardCol + DIRS[guardDir][1]
            if (isValid(nextRow, nextCol) && map[nextRow][nextCol]) {
                guardDir = (guardDir + 1).mod(4)
            }
            guardRow = guardRow + DIRS[guardDir][0]
            guardCol = guardCol + DIRS[guardDir][1]
        }
        return visited.size
    }

    fun part2(input: List<String>): Int {
        val n = input.size
        val m = input[0].length
        fun isValid(row: Int, col: Int) = row in 0..<n && col in 0..<m
        var row = 0
        var col = 0
        var dir = 0
        val grid = Array(n) { BooleanArray(m) }
        input.forEachIndexed { lineIndex, line ->
            line.forEachIndexed { colIndex, c ->
                if (c == '#') grid[lineIndex][colIndex] = true
                if (c == '^') {
                    row = lineIndex
                    col = colIndex
                }
            }
        }
        val DIRS = arrayOf(
            intArrayOf(-1, 0),
            intArrayOf(0, 1),
            intArrayOf(1, 0),
            intArrayOf(0, -1),
        )
        val visited = Array(n) { Array(m) { BooleanArray(4) } }
        var selected = 0
        while (isValid(row, col)) {
            visited[row][col][dir] = true
            var nextRow = row + DIRS[dir][0]
            var nextCol = col + DIRS[dir][1]
            // rotate only to locate next cell, but also update next for convenience
            while (isValid(nextRow, nextCol) && grid[nextRow][nextCol]) {
                dir = (dir + 1).mod(4)
                visited[row][col][dir] = true
                nextRow = row + DIRS[dir][0]
                nextCol = col + DIRS[dir][1]
            }
            if (isValid(nextRow, nextCol) && !grid[nextRow][nextCol]) {
                // do not place in a cell that is visited at all, because it would be a paradox
                // including guard's start position
                if (visited[nextRow][nextCol].all { it == false }) {
                    grid[nextRow][nextCol] = true
                    val branchVisited = visited.map { r -> r.map { a -> a.map { it }.toMutableList() }}
                    var branchRow = row
                    var branchCol = col
                    var branchDir = dir
                    // re-do the entry into this position, with the obstacle
                    branchVisited[branchRow][branchCol][branchDir] = false
                    while (isValid(branchRow, branchCol)) {
                        if (branchVisited[branchRow][branchCol][branchDir]) {
                            selected++
                            break
                        }
                        branchVisited[branchRow][branchCol][branchDir] = true
                        // rotate before move
                        while (isValid(branchRow + DIRS[branchDir][0], branchCol + DIRS[branchDir][1])
                        && grid[branchRow + DIRS[branchDir][0]][branchCol + DIRS[branchDir][1]]) {
                            branchDir = (branchDir + 1).mod(4)
                            branchVisited[branchRow][branchCol][branchDir] = true
                        }
                        branchRow = branchRow + DIRS[branchDir][0]
                        branchCol = branchCol + DIRS[branchDir][1]
                    }
                    grid[nextRow][nextCol] = false
                }
            }
            row = row + DIRS[dir][0]
            col = col + DIRS[dir][1]
        }
        return selected
    }

    val testInput = readInput("Day06_test")
    val input = readInput("Day06")

    part1(testInput).println()
    part1(input).println()

    part2(testInput).println()
    part2(input).println()
}
