import kotlin.math.*

fun main() {
    fun part1(input: List<String>): Int {
        data class Coordinate(val r: Int, val c: Int)
        val antennas = mutableMapOf<Char, MutableList<Coordinate>>()
        input.forEachIndexed { r, line ->
            line.forEachIndexed { c, ch ->
                if (ch != '.') {
                    antennas.getOrPut(ch) { mutableListOf() }.add(Coordinate(r = r, c = c))
                }
            }
        }
        val n = input.size
        val m = input[0].length
        fun isValid(r: Int, c: Int) = r in 0..<n && c in 0..<m
        val antinodes = mutableSetOf<Int>()
        antennas.forEach { _, locations ->
            for (i in 0..<locations.size) {
                for (j in (i + 1)..<locations.size) {
                    var diffR = locations[j].r - locations[i].r
                    var diffC = locations[j].c - locations[i].c
                    if (isValid(locations[i].r + 2 * diffR, locations[i].c + 2 * diffC)) {
                        antinodes.add((locations[i].r + 2 * diffR) * m + (locations[i].c + 2 * diffC))
                    }
                    diffR = locations[i].r - locations[j].r
                    diffC = locations[i].c - locations[j].c
                    if (isValid(locations[j].r + 2 * diffR, locations[j].c + 2 * diffC)) {
                        antinodes.add((locations[j].r + 2 * diffR) * m + (locations[j].c + 2 * diffC))
                    }
                }
            }
        }
        return antinodes.size
    }

    fun part2(input: List<String>): Int {
        val antennas = mutableMapOf<Char, MutableList<Pair<Int, Int>>>()
        input.forEachIndexed { r, line ->
            line.forEachIndexed { c, ch ->
                if (ch != '.') {
                    antennas.getOrPut(ch) { mutableListOf() }.add(r to c)
                }
            }
        }
        val n = input.size
        val m = input[0].length
        fun isValid(r: Int, c: Int) = r in 0..<n && c in 0..<m
        val antinodes = mutableSetOf<Int>()
        antennas.forEach { _, locations ->
            for (i in 0..<locations.size) {
                for (j in (i + 1)..<locations.size) {
                    val diffR = locations[i].first - locations[j].first
                    val diffC = locations[i].second - locations[j].second
                    var count = 1
                    while (isValid(locations[i].first + diffR * count, locations[i].second + diffC * count)) {
                        antinodes.add((locations[i].first + diffR * count) * m + (locations[i].second + diffC * count))
                        count++
                    }
                    count = 0
                    while (isValid(locations[i].first + diffR * count, locations[i].second + diffC * count)) {
                        antinodes.add((locations[i].first + diffR * count) * m + (locations[i].second + diffC * count))
                        count--
                    }
                }
            }
        }
        return antinodes.size
    }

    val testInput = readInput("Day08_test")
    val input = readInput("Day08")

    part1(testInput).println()
    part1(input).println()

    part2(testInput).println()
    part2(input).println()
}
