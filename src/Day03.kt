import kotlin.math.*

fun main() {
    fun part1(input: List<String>): Int {
        var answer = 0
        for (l in input.indices) {
            val s = input[l]
            var i = 0
            while (i < s.length) {
                if (s[i] != 'm') {
                    i = s.indexOf('m', i)
                    if (i == -1) break
                    continue
                }
                i++
                if (s[i] != 'u') continue
                i++
                if (s[i] != 'l') continue
                i++
                if (s[i] != '(') continue
                i++
                
                var operand1 = 0
                var operand1Digits = 0
                while (operand1Digits < 3 && s[i].isDigit()) {
                    operand1 = operand1 * 10 + (s[i++] - '0')
                    operand1Digits++
                }
                if (operand1Digits == 0) continue

                if (s[i] != ',') continue
                i++

                var operand2 = 0
                var operand2Digits = 0
                while (operand2Digits < 3 && s[i].isDigit()) {
                    operand2 = operand2 * 10 + (s[i++] - '0')
                    operand2Digits++
                }
                if (operand2Digits == 0) continue

                if (s[i] != ')') continue
                i++

                answer += operand1 * operand2

                i = s.indexOf('m', i)
                if (i == -1) break
            }
        }
        return answer
    }

    fun part2(input: List<String>): Int {
        var answer = 0
        var isEnabled = true
        for (l in input.indices) {
            val s = input[l]
            var i = 0
            while (i < s.length) {
                if (isEnabled && s[i] == 'm') {
                    i++
                    if (s[i] != 'u') continue
                    i++
                    if (s[i] != 'l') continue
                    i++
                    if (s[i] != '(') continue
                    i++
                    
                    var operand1 = 0
                    var operand1Digits = 0
                    while (operand1Digits < 3 && s[i].isDigit()) {
                        operand1 = operand1 * 10 + (s[i++] - '0')
                        operand1Digits++
                    }
                    if (operand1Digits == 0) continue

                    if (s[i] != ',') continue
                    i++

                    var operand2 = 0
                    var operand2Digits = 0
                    while (operand2Digits < 3 && s[i].isDigit()) {
                        operand2 = operand2 * 10 + (s[i++] - '0')
                        operand2Digits++
                    }
                    if (operand2Digits == 0) continue

                    if (s[i] != ')') continue
                    i++

                    answer += operand1 * operand2
                } else if (s[i] == 'd') {
                    i++
                    if (s[i] != 'o') continue
                    i++
                    if (s[i] == '(') {
                        i++
                        if (s[i] != ')') continue
                        i++
                        isEnabled = true
                    } else if (s[i] == 'n') {
                        i++
                        if (s[i] != '\'') continue
                        i++
                        if (s[i] != 't') continue
                        i++
                        if (s[i] != '(') continue
                        i++
                        if (s[i] != ')') continue
                        i++
                        isEnabled = false
                    } else {
                        continue
                    }
                } else if (isEnabled) {
                    val nextM = s.indexOf('m', i)
                    val nextD = s.indexOf('d', i)
                    if (nextM == -1 && nextD == -1) {
                        break
                    } else if (nextM == -1) {
                        i = nextD
                    } else if (nextD == -1) {
                        i = nextM
                    } else {
                        i = min(nextM, nextD)
                    }
                } else {
                    val nextD = s.indexOf('d', i)
                    if (nextD == -1) {
                        break
                    } else {
                        i = nextD
                    }
                }
            }
        }
        return answer
    }

    val testInput = readInput("Day03_test")
    val input = readInput("Day03")

    // part1(testInput).println()
    // part1(input).println()

    part2(testInput).println()
    part2(input).println()
}

// bugfix: need to go back to the top without consuming current char
// when meeting an unexpected char
// bugfix: consume multiple lines
// bugfix: trying to jump to next M (infinite loop) even when disabled
