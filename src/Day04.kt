import kotlin.math.*

fun main() {
    val DIRS = arrayOf(
        intArrayOf(0, -1),
        intArrayOf(-1, -1),
        intArrayOf(-1, 0),
        intArrayOf(-1, 1),
        intArrayOf(0, 1),
        intArrayOf(1, 1),
        intArrayOf(1, 0),
        intArrayOf(1, -1),
    )
    fun part1(input: List<String>): Int {
        val target = "XMAS"
        val n = input.size
        val m = input[0].length
        var answer = 0
        for (i in 0..<n) {
            for (j in 0..<m) {
                if (input[i][j] != target[0]) continue
                for (dir in DIRS) {
                    val lastR = i + dir[0] * 3
                    val lastC = j + dir[1] * 3
                    if (lastR in 0..<n && lastC in 0..<m) {
                        var ok = true
                        for (d in 0..3) {
                            val nextR = i + dir[0] * d
                            val nextC = j + dir[1] * d
                            if (input[nextR][nextC] != target[d]) {
                                ok = false
                            }
                        }
                        if (ok) answer++
                    }
                }
            }
        }
        return answer
    }

    val DIRS2 = arrayOf(
        intArrayOf(-1, -1),
        intArrayOf(-1, 1),
        intArrayOf(1, 1),
        intArrayOf(1, -1),
    )
    fun part2(input: List<String>): Int {
        val steps = intArrayOf(-1, 1)
        val target = "MS"
        val n = input.size
        val m = input[0].length
        var answer = 0
        for (i in 1..<n - 1) {
            for (j in 1..<m - 1) {
                if (input[i][j] != 'A') continue
                var matches = 0
                for (dir in DIRS2) {
                    var ok = true
                    if (input[i + dir[0]][j + dir[1]] != target[0]
                            || input[i - dir[0]][j - dir[1]] != target[1]) {
                        ok = false
                    }
                    if (ok) matches++
                }
                if (matches == 2) answer++
            }
        }
        return answer
    }

    val testInput = readInput("Day04_test")
    val input = readInput("Day04")

    part1(testInput).println()
    part1(input).println()

    part2(testInput).println()
    part2(input).println()
}
