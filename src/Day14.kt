import java.util.*

import kotlin.math.*

fun main() {
    data class Robot(val pX: Int, val pY: Int, val vX: Int, val vY: Int)

    fun parse(input: List<String>): List<Robot> {
        val result = mutableListOf<Robot>()
        var i = 0
        while (i < input.size) {
            val l1 = input[i].split(" ", "=", ",")
            result.add(Robot(
                pX = l1[1].toInt(),
                pY = l1[2].toInt(),
                vX = l1[4].toInt(),
                vY = l1[5].toInt(),
            ))
            i++
        }
        return result
    }

    fun part1(input: List<String>, xTotal: Int, yTotal: Int, time: Int): Long {
        // 0--> x
        // |
        // v y
        val robots = parse(input)
        val xMid = xTotal / 2
        val yMid = yTotal / 2
        val quadrants = IntArray(4)
        robots.forEach { robot ->
            val newX = (robot.pX.toLong() + time.toLong() * robot.vX).mod(xTotal)
            val newY = (robot.pY.toLong() + time.toLong() * robot.vY).mod(yTotal)
            if (newY < yMid) {
                if (newX < xMid) {
                    quadrants[0]++
                } else if (newX > xMid) {
                    quadrants[1]++
                }
            } else if (newY > yMid) {
                if (newX < xMid) {
                    quadrants[2]++
                } else if (newX > xMid) {
                    quadrants[3]++
                }
            }
        }
        return quadrants[0].toLong() * quadrants[1] * quadrants[2] * quadrants[3]
    }

    fun part2(input: List<String>, xTotal: Int, yTotal: Int, timeMax: Int) {
        // 0--> x
        // |
        // v y
        val robots = parse(input)
        repeat(timeMax) { time ->
            val data = Array(yTotal) { CharArray(xTotal) { ' ' } }
            robots.forEach { robot ->
                val newX = (robot.pX.toLong() + time.toLong() * robot.vX).mod(xTotal)
                val newY = (robot.pY.toLong() + time.toLong() * robot.vY).mod(yTotal)
                data[newY][newX] = 'X'
            }
            println("==================================================")
            println("time = $time")
            data.forEach { line ->
                println(line.joinToString(""))
            }
        }
    }

    val testInput = readInput("Day14_test")
    val input = readInput("Day14")

    part1(testInput, 11, 7, 100).println()
    part1(input, 101, 103, 100).println()

    part2(input, 101, 103, 10403)
}
