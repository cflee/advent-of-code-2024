import java.util.*

import kotlin.math.*
import kotlin.collections.ArrayDeque

fun main() {
    val MOD = 16777216L
    fun generate(input: Long): Long {
        var secret = input
        secret = secret.xor(secret * 64) % MOD
        secret = secret.xor(secret / 32) % MOD
        secret = secret.xor(secret * 2048) % MOD
        return secret
    }

    fun part1(input: List<String>): Long {
        val buyers = input.map { it.toLong() }
        var answer = 0L
        buyers.forEach { buyer ->
            var secret = buyer
            repeat(2000) {
                secret = generate(secret)
            }
            answer += secret
        }
        return answer
    }

    fun part2(input: List<String>): Long {
        val buyers = input.map { it.toLong() }
        
        data class Seq(val s1: Int, val s2: Int, val s3: Int, val s4: Int)
        val memo = mutableMapOf<Seq, IntArray>()
        buyers.forEachIndexed { buyerIndex, buyer ->
            var secret = buyer
            val changes = ArrayDeque<Int>()
            repeat(2000) {
                val curSecret = generate(secret)
                val prevPrice = (secret % 10).toInt()
                val curPrice = (curSecret % 10).toInt()
                val change = curPrice - prevPrice
                changes.addLast(change)
                if (changes.size > 4) {
                    changes.removeFirst()
                }
                if (changes.size == 4) {
                    val seq = Seq(changes[0], changes[1], changes[2], changes[3])
                    val memoData = memo.getOrPut(seq) { IntArray(buyers.size) { -1 } }
                    if (memoData[buyerIndex] == -1) {
                        memoData[buyerIndex] = curPrice
                    }
                }
                secret = curSecret
            }
        }
        var answer = 0L
        memo.forEach { _, memoData ->
            answer = max(answer, memoData.filter { it != -1 }.map { it.toLong() }.sum())
        }
        return answer.toLong()
    }

    val testInput = readInput("Day22_test")
    val input = readInput("Day22")

    part1(testInput).println()
    part1(input).println()

    part2(testInput).println()
    part2(input).println()
}
