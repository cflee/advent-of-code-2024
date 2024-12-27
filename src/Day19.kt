import java.util.*

import kotlin.math.*

fun main() {
    data class TrieNode(
    var isEnd: Boolean,
    val children: Array<TrieNode?> = Array(26) { null }
)

    fun insert(root: TrieNode, s: String) {
        var cur = root
        var i = 0
        while (i < s.length) {
            if (cur.children[s[i] - 'a'] == null) {
                cur.children[s[i] - 'a'] = TrieNode(isEnd = false)
            }
            cur = cur.children[s[i] - 'a']!!
            i++
        }
        cur.isEnd = true
    }

    fun part1(input: List<String>): Int {
        val towels = input[0].split(", ")
        val designs = input.subList(2, input.size)
        
        val root = TrieNode(isEnd = false)
        towels.forEach { insert(root, it) }
        
        var answer = 0
        designs.forEach { design ->
            val memo = mutableMapOf<Int, Boolean>()
            fun recurse(index: Int): Boolean {
                if (memo.containsKey(index)) {
                    return memo[index]!!
                }
                if (index == design.length) {
                    memo[index] = true
                    return true
                }
                var i = index // next char to navigate
                var node: TrieNode? = root
                while (i < design.length) {
                    if (node == null) {
                        break
                    }
                    node = node.children[design[i] - 'a']
                    i++
                    if (node != null && node.isEnd) {
                        if (recurse(i)) {
                            // terminate early
                            memo[index] = true
                            return true
                        }
                    }
                }
                memo[index] = false
                return false
            }
            if (recurse(0)) {
                answer++
            }
        }
        return answer
    }

    fun part2(input: List<String>): Long {
        val towels = input[0].split(", ")
        val designs = input.subList(2, input.size)
        
        val root = TrieNode(isEnd = false)
        towels.forEach { insert(root, it) }
        
        var answer = 0L
        designs.forEach { design ->
            val memo = mutableMapOf<Int, Long>()
            fun recurse(index: Int): Long {
                if (memo.containsKey(index)) {
                    return memo[index]!!
                }
                if (index == design.length) {
                    memo[index] = 1
                    return 1
                }
                var i = index // next char to navigate
                var node: TrieNode? = root
                var result = 0L
                while (i < design.length) {
                    if (node == null) {
                        break
                    }
                    node = node!!.children[design[i] - 'a']
                    i++
                    if (node != null && node!!.isEnd) {
                        result += recurse(i)
                    }
                }
                memo[index] = result
                return result
            }
            answer += recurse(0)
        }
        return answer
    }

    val testInput = readInput("Day19_test")
    val input = readInput("Day19")

    part1(testInput).println()
    part1(input).println()

    part2(testInput).println()
    part2(input).println()
}
