import java.util.*

import kotlin.math.*

fun main() {
    fun part1(input: List<String>): Long {
        data class File(val len: Int, val fileId: Int)
        val files = mutableListOf<File>()
        input[0].forEachIndexed { blockIndex, c ->
            if (blockIndex.mod(2) == 0) {
                // file
                files.add(File(c - '0', blockIndex / 2))
            } else {
                // free space
                files.add(File(c - '0', -1))
            }
        }
        val data = mutableListOf<Int>()
        files.forEach { file ->
            repeat(file.len) {
                data.add(file.fileId)
            }
        }
        var l = 0
        var r = data.size - 1
        while (l < r) {
            if (data[l] != -1) {
                l++
                continue
            }
            while (r > l && data[r] == -1) {
                r--
            }
            if (r != l) {
                data[l] = data[r]
                r--
            }
            l++
        }
        var checksum = 0L
        for (i in 0..r) {
            if (data[i] != -1) checksum += i * data[i]
        }
        return checksum
    }

    fun part2(input: List<String>): Long {
        data class File(var len: Int, var fileId: Int)
        val segments = mutableListOf<File>()
        input[0].forEachIndexed { blockIndex, c ->
            val len = c - '0'
            if (blockIndex.mod(2) == 0) {
                // file
                segments.add(File(len, blockIndex / 2))
            } else {
                // free space
                segments.add(File(len, -1))
            }
        }
        
        val newSegments = mutableListOf<File>()
        segments.forEachIndexed { segmentIndex, segment ->
            if (segment.fileId != -1) {
                newSegments.add(segment)
                return@forEachIndexed
            }
            var balance = segment.len
            while (balance > 0) {
                var r = segments.size - 1
                while (r > segmentIndex && (segments[r].fileId == -1 || segments[r].len > balance)) {
                    r--
                }
                if (r <= segmentIndex) {
                    break
                }
                newSegments.add(File(segments[r].len, segments[r].fileId))
                balance -= segments[r].len
                segments[r].fileId = -1
            }
            if (balance > 0) {
                newSegments.add(File(balance, -1))
            }
        }
        var checksum = 0L
        var blockId = 0
        newSegments.forEach { segment ->
            repeat(segment.len) {
                if (segment.fileId != -1) {
                    checksum += (blockId++) * segment.fileId
                } else {
                    blockId++
                }
            }
        }
        return checksum
    }

    val testInput = readInput("Day09_test")
    val input = readInput("Day09")

    part1(testInput).println()
    part1(input).println()

    part2(testInput).println()
    part2(input).println()
}
