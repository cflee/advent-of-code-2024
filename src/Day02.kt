import kotlin.math.*

fun main() {
    fun part1(input: List<String>): Int {
        val reports = input.map { line ->
            line.split(" ").map { it.toInt() }
        }
        var answer = 0
        reports.forEach { report ->
            if (report[0] == report[1]) return@forEach
            val isIncreasing = report[0] < report[1]
            var isOk = true
            for (i in 1..<report.size) {
                if ((isIncreasing && report[i - 1] >= report[i])
                        || (!isIncreasing && report[i - 1] <= report[i])
                        || (abs(report[i] - report[i - 1]) >= 4)
                        ) {
                    isOk = false
                    break
                }
            }
            if (isOk) {
                answer++
            }
        }
        return answer
    }

    fun part2(input: List<String>): Int {
        fun check(report: List<Int>): Boolean {
            if (report[0] == report[1]) return false
            val isIncreasing = report[0] < report[1]
            var isOk = true
            for (i in 1..<report.size) {
                if ((isIncreasing && report[i - 1] >= report[i])
                        || (!isIncreasing && report[i - 1] <= report[i])
                        || (abs(report[i] - report[i - 1]) >= 4)
                        ) {
                    isOk = false
                    break
                }
            }
            return isOk
        }
        val reports = input.map { line ->
            line.split(" ").map { it.toInt() }
        }
        var answer = 0
        reports.forEach { report ->
            if (check(report)) {
                answer++
                return@forEach
            }
            for (skip in 0..<report.size) {
                val report2 = mutableListOf<Int>()
                for (i in 0..<report.size) {
                    if (i == skip) continue
                    report2.add(report[i])
                }
                if (check(report2)) {
                    answer++
                    return@forEach
                }
            }
        }
        return answer
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day02_test")
    part1(testInput).println()
    part2(testInput).println()

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
