import java.util.*

import kotlin.math.*

fun main() {
    fun part1(input: List<String>): String {
        var ip = 0
        val registers = IntArray(3)

        registers[0] = input[0].split(": ")[1].toInt()
        registers[1] = input[1].split(": ")[1].toInt()
        registers[2] = input[2].split(": ")[1].toInt()

        val program = input[4].split(": ")[1].split(",").map { it.toInt() }
        val output = mutableListOf<Int>()

        fun getComboOperand(operand: Int): Int {
            if (operand in 0..3) {
                return operand
            } else if (operand == 4) {
                return registers[0]
            } else if (operand == 5) {
                return registers[1]
            } else if (operand == 6) {
                return registers[2]
            } else {
                // 7 is reserved
                return 0
            }
        }

        while (ip < program.size) {
            val instr = program[ip]
            val operand = program[ip + 1]
            if (instr == 0) {
                // adv
                registers[0] = (registers[0].toDouble() / 2.0.pow(getComboOperand(operand))).toInt()
                ip += 2
            } else if (instr == 1) {
                // bxl
                registers[1] = registers[1].xor(operand)
                ip += 2
            } else if (instr == 2) {
                // bst
                registers[1] = getComboOperand(operand) % 8
                ip += 2
            } else if (instr == 3) {
                // jnz
                if (registers[0] != 0) {
                    ip = operand
                } else {
                    // do nothing
                    ip += 2
                }
            } else if (instr == 4) {
                // bxc
                registers[1] = registers[1].xor(registers[2])
                ip += 2
            } else if (instr == 5) {
                // out
                val result = getComboOperand(operand) % 8
                output.add(result)
                ip += 2
            } else if (instr == 6) {
                // bdv
                registers[1] = (registers[0].toDouble() / 2.0.pow(getComboOperand(operand))).toInt()
                ip += 2
            } else if (instr == 7) {
                // cdv
                registers[2] = (registers[0].toDouble() / 2.0.pow(getComboOperand(operand))).toInt()
                ip += 2
            }
        }
        return output.joinToString(",")
    }

    // https://stackoverflow.com/a/64168013
    fun Int.to32bitString(): String = Integer.toBinaryString(this).padStart(Int.SIZE_BITS, '0')

    fun part2(input: List<String>): Long {
        // output bytes => relevant fragment that generates that output
        val lookup = Array(8) { mutableSetOf<String>() }
        // up to 10 bits may be used (3 bits to the left of rshift 0-7 bits)
        for (i in 0..0b11_1111_1111) {
            val aLsb3 = i.and(0b111)
            val shiftAmount = aLsb3.xor(0b100)
            val aShifted = i.shr(shiftAmount)
            val output = aLsb3.xor(aShifted).and(0b111)

            // snip to only match on the bits used
            val binaryString = i.to32bitString()
            val relevant = binaryString.substring(binaryString.length - (shiftAmount + 3))
            lookup[output].add(relevant)
        }

        val program = input[4].split(": ")[1].split(",").map { it.toInt() }
        var answer = Long.MAX_VALUE
        val selected = mutableListOf<String>()
        fun calc(progIndex: Int, required: String) {
            if (progIndex >= program.size) {
                if (required.any { it == '1' }) {
                    // required must be empty, or all 0s
                    return
                }
                if (selected.last() == "000") {
                    // but if the last desired instruction is 000, it won't be executed due to the jnz loop
                    return
                }
                var selectedNum = 0L
                selected.reversed().forEach {
                    selectedNum = selectedNum.shl(3).or(it.toLong(2))
                }
                answer = min(answer, selectedNum)
                return
            }
            lookup[program[progIndex]].forEach { cand ->
                val longer = if (cand.length >= required.length) cand else required
                val shorter = if (cand.length >= required.length) required else cand
                if (longer.endsWith(shorter)) {
                    selected.addLast(longer.substring(longer.length - 3))
                    calc(progIndex + 1, longer.substring(0, max(0, longer.length - 3)))
                    selected.removeLast()
                }
            }
        }
        calc(0, "")
        return answer
    }

    // val testInput = readInput("Day17_test")
    val input = readInput("Day17")

    // part1(testInput).println()
    // part1(input).println()

    // part2(testInput).println()
    part2(input).println()
}
