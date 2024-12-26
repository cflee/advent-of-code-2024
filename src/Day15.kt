import java.util.*

import kotlin.math.*

fun main() {
    fun parse(input: List<String>): Pair<List<CharArray>, String> {
        val warehouse = mutableListOf<CharArray>()
        var i = 0
        val mapWidth = input[0].length
        while (input[i].length == mapWidth) {
            warehouse.add(input[i].toCharArray())
            i++
        }
        i++
        val moves = StringBuilder()
        while (i < input.size) {
            moves.append(input[i])
            i++
        }
        return warehouse to moves.toString()
    }

    fun part1(input: List<String>): Long {
        /*
        0--> col
        |
        v row
         */
        val DIRS = mapOf(
            '^' to intArrayOf(-1, 0),
            'v' to intArrayOf(1, 0),
            '<' to intArrayOf(0, -1),
            '>' to intArrayOf(0, 1),
        )
        val (warehouse, moves) = parse(input)
        var robotRow = 0
        var robotCol = 0
        outerLoop@ for (r in warehouse.indices) {
            for (c in 0..<warehouse[r].size) {
                if (warehouse[r][c] == '@') {
                    robotRow = r
                    robotCol = c
                    warehouse[r][c] = '.'
                    break@outerLoop
                }
            }
        }
        moves.forEach { move ->
            val dir = DIRS[move]!!
            var row = robotRow + dir[0]
            var col = robotCol + dir[1]
            var pushingBox = false
            while (warehouse[row][col] == 'O') {
                pushingBox = true
                row += dir[0]
                col += dir[1]
            }
            if (warehouse[row][col] == '#') {
                // can't go anywhere
            } else if (warehouse[row][col] == '.') {
                // can go
                if (pushingBox) {
                    warehouse[row][col] = 'O'
                    warehouse[robotRow + dir[0]][robotCol + dir[1]] = '.'
                }
                robotRow += dir[0]
                robotCol += dir[1]
            }
        }
        var answer = 0L
        warehouse.forEachIndexed { row, line ->
            line.forEachIndexed { col, cell ->
                if (cell == 'O') answer += 100 * row + col
            }
        }
        return answer
    }

    fun part2(input: List<String>): Long {
        /*
        0--> col
        |
        v row
         */
        val DIRS = mapOf(
            '^' to intArrayOf(-1, 0),
            'v' to intArrayOf(1, 0),
            '<' to intArrayOf(0, -1),
            '>' to intArrayOf(0, 1),
        )
        val (warehouseOrig, moves) = parse(input)
        val warehouse = warehouseOrig.map { line ->
            val arr = CharArray(2 * line.size)
            var i = 0
            line.forEach { ch ->
                if (ch == '#') {
                    arr[i++] = '#'
                    arr[i++] = '#'
                } else if (ch == 'O') {
                    arr[i++] = '['
                    arr[i++] = ']'
                } else if (ch == '.') {
                    arr[i++] = '.'
                    arr[i++] = '.'
                } else if (ch == '@') {
                    arr[i++] = '@'
                    arr[i++] = '.'
                }
            }
            arr
        }

        var robotRow = 0
        var robotCol = 0
        outerLoop@ for (r in warehouse.indices) {
            for (c in 0..<warehouse[r].size) {
                if (warehouse[r][c] == '@') {
                    robotRow = r
                    robotCol = c
                    warehouse[r][c] = '.'
                    break@outerLoop
                }
            }
        }
        
        moves.forEach { move ->
            val dir = DIRS[move]!!
            if (move == '<' || move == '>') {
                // not affected by wide objects
                var row = robotRow + dir[0]
                var col = robotCol + dir[1]
                while (warehouse[row][col] != '#' && warehouse[row][col] != '.') {
                    // is boxes []
                    row += dir[0]
                    col += dir[1]
                }
                if (warehouse[row][col] == '#') {
                    // can't go anywhere
                } else if (warehouse[row][col] == '.') {
                    // can go
                    for (col2 in col downTo (robotCol + dir[1])) {
                        warehouse[row][col2] = warehouse[row][col2 - dir[1]]
                    }
                    for (col2 in col..(robotCol + dir[1])) {
                        warehouse[row][col2] = warehouse[row][col2 - dir[1]]
                    }
                    // first box spot to be cleared out for the robot
                    warehouse[row][robotCol + dir[1]] = '.'
                    robotRow += dir[0]
                    robotCol += dir[1]
                }
            } else {
                // up and down moves only, affected by wide objects
                // ] means NW for up move, SW for down move
                val rightBracketDir = if (move == '^') intArrayOf(-1, -1) else intArrayOf(1, -1)
                // [ means NE for up move, SE for down move
                val leftBracketDir = if (move == '^') intArrayOf(-1, 1) else intArrayOf(1, 1)
                
                // distance to column indices. distances are 0-indexed
                val impactedCells = mutableListOf<MutableList<Int>>()
                val distClear = mutableListOf<Boolean>()
                
                // instantiate with the first position to be visited, could be a box
                val startRow = robotRow + dir[0]
                val startCol = robotCol + dir[1]
                impactedCells.add(mutableListOf(startCol))
                if (warehouse[startRow][startCol] == '[') {
                    impactedCells[0].add(robotCol + leftBracketDir[1])
                } else if (warehouse[startRow][startCol] == ']') {
                    impactedCells[0].add(robotCol + rightBracketDir[1])
                }
                distClear.add(true)

                // iterate over distances
                var dist = 0
                while (dist < impactedCells.size) {
                    val row = robotRow + (dist + 1) * dir[0]
                    val impactedList = impactedCells[dist]
                    var wallFound = false
                    impactedList.forEach { col ->
                        // box or wall accounting
                        if (warehouse[row][col] != '.') {
                            // whether it's box or wall, still blocks the movement into this dist
                            distClear[dist] = false
                        }
                        // if there's any wall, we should terminate early, no point exploring further
                        // as the whole "tree" must move together
                        if (warehouse[row][col] == '#') {
                            wallFound = true
                        }
                        // look into the next dist
                        // if this is currently a box, then need to propagate to next dist
                        // always put the next one in, regardless of shape
                        // but if there is a box, then may need to put adjacent in
                        if (warehouse[row][col] == '[' || warehouse[row][col] == ']') {
                            if (dist + 1 >= impactedCells.size) {
                                impactedCells.add(mutableListOf())
                                distClear.add(true)
                            }
                            val nextRow = row + dir[0]
                            val nextCol = col + dir[1]
                            impactedCells[dist + 1].add(nextCol)
                            if (warehouse[nextRow][nextCol] == '[') {
                                impactedCells[dist + 1].add(col + leftBracketDir[1])
                            } else if (warehouse[nextRow][nextCol] == ']') {
                                impactedCells[dist + 1].add(col + rightBracketDir[1])
                            }
                        }
                    }
                    if (wallFound) {
                        if (impactedCells.size > dist + 1) {
                            // cleanup when don't want to consider next dist
                            impactedCells.removeLast()
                            distClear.removeLast()
                        }
                        break
                    }
                    dist++
                }
                if (distClear.last()) {
                    // can go
                    // move stuff
                    // skip the last dist, to "push" from the second last dist (last with objects)
                    dist--
                    while (dist >= 0) {
                        val row = robotRow + (dist + 1) * dir[0]
                        val nextRow = row + dir[0]
                        impactedCells[dist].forEach { col ->
                            if (warehouse[row][col] == '[' || warehouse[row][col] == ']') {
                                warehouse[nextRow][col] = warehouse[row][col]
                                warehouse[row][col] = '.'
                            }
                        }
                        dist--
                    }
                    // move robot
                    robotRow += dir[0]
                    robotCol += dir[1]
                }
            }
        }
        var answer = 0L
        warehouse.forEachIndexed { row, line ->
            line.forEachIndexed { col, cell ->
                if (cell == '[') {
                    answer += 100 * row + col
                }
            }
        }
        return answer
    }

    val testInput = readInput("Day15_test")
    val input = readInput("Day15")

    part1(testInput).println()
    part1(input).println()

    part2(testInput).println()
    part2(input).println()
}
