import java.util.*

import kotlin.math.*

fun main() {
    data class Machine(val aX: Int, val aY: Int, val bX: Int, val bY: Int, val prizeX: Long, val prizeY: Long)

    fun parse(input: List<String>): List<Machine> {
        val result = mutableListOf<Machine>()
        var i = 0
        while (i < input.size) {
            val l1 = input[i].split(" ", ",", "+")
            val l2 = input[i + 1].split(" ", ",", "+")
            val l3 = input[i + 2].split(" ", ",", "=")
            result.add(Machine(
                aX = l1[3].toInt(),
                aY = l1[6].toInt(),
                bX = l2[3].toInt(),
                bY = l2[6].toInt(),
                prizeX = l3[2].toLong(),
                prizeY = l3[5].toLong(),
            ))
            i += 4
        }
        return result
    }

    fun part1(input: List<String>): Long {
        val machines = parse(input)
        var answer = 0L
        machines.forEach { machine ->
            // var result = Long.MAX_VALUE
            // fun recurse(x: Int, y: Int, aCount: Int, bCount: Int) {
            //     if (aCount > 2 || bCount > 2) {
            //         return
            //     }
            //     if (x == machine.prizeX && y == machine.prizeY) {
            //         result = min(result, aCount.toLong() * 3 + bCount)
            //         return
            //     }
            //     if (aCount.toLong() * 3 + bCount > result) {
            //         return
            //     }
            //     if (x > machine.prizeX || y > machine.prizeY) {
            //         return
            //     }
            //     println("x=$x y=$y aCount=$aCount bCount=$bCount")
            //     recurse(x + machine.aX, y + machine.aY, aCount + 1, bCount)
            //     recurse(x + machine.bX, y + machine.bY, aCount, bCount + 1)
            // }
            // recurse(0, 0, 0, 0)
            // answer += result

            var result = Long.MAX_VALUE
            for (aCount in 0..100) {
                for (bCount in 0..100) {
                    val x = aCount * machine.aX + bCount * machine.bX
                    val y = aCount * machine.aY + bCount * machine.bY
                    if (x.toLong() == machine.prizeX && y.toLong() == machine.prizeY) {
                        result = min(result, aCount.toLong() * 3 + bCount)
                        break
                    }
                    if (x > machine.prizeX || y > machine.prizeY) {
                        break
                    }
                }
            }
            if (result != Long.MAX_VALUE) {
                answer += result
            }
        }
        return answer
    }

    fun part2(input: List<String>): Long {
        val machines = parse(input)
        var answer = 0L
        machines.forEach { machine ->
            val prizeX = 10000000000000L + machine.prizeX
            val prizeY = 10000000000000L + machine.prizeY
            /*
            aX * aCount + bX * bCount = prizeX
            aY * aCount + bY * bCount = prizeY
            by https://en.wikipedia.org/wiki/Cramer%27s_rule#Explicit_formulas_for_small_systems
            aCount = (prizeX * bY - bX * prizeY) / (aX * bY - bX * aY)
            bCount = (aX * prizeY - prizeX * aY) / (aX * bY - bX * aY)
             */
            val origDeterminant = machine.aX.toLong() * machine.bY - machine.bX.toLong() * machine.aY
            val aDeterminant = (prizeX * machine.bY - machine.bX.toLong() * prizeY)
            val bDeterminant = (machine.aX.toLong() * prizeY - prizeX.toLong() * machine.aY.toLong())
            val aSolution = aDeterminant / origDeterminant
            val bSolution = bDeterminant / origDeterminant
            if (aSolution * origDeterminant == aDeterminant && bSolution * origDeterminant == bDeterminant) {
                answer += aSolution * 3 + bSolution
            }
        }
        return answer
    }

    val testInput = readInput("Day13_test")
    val input = readInput("Day13")

    part1(testInput).println()
    part1(input).println()

    part2(testInput).println()
    part2(input).println()
}
