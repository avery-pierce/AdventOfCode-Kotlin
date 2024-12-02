package puzzles

import utils.*
import kotlin.math.abs

class Puzzle2402: Puzzle() {
    override fun solveA(input: Input): Any {
        return Parser(input).reports()
            .filter { it.isSafe() }
            .count()
    }

    override val testCasesA: List<TestCase>
        get() = listOf(
            TestCase("7 6 4 2 1\n" +
                    "1 2 7 8 9\n" +
                    "9 7 6 2 1\n" +
                    "1 3 2 4 5\n" +
                    "8 6 4 4 1\n" +
                    "1 3 6 7 9", 2)
        )

    override fun solveB(input: Input): Any {
        return Parser(input).reports(true)
            .filter { it.isSafe() }
            .count()
    }

    override val testCasesB: List<TestCase>
        get() = listOf(
            TestCase("7 6 4 2 1\n" +
                    "1 2 7 8 9\n" +
                    "9 7 6 2 1\n" +
                    "1 3 2 4 5\n" +
                    "8 6 4 4 1\n" +
                    "1 3 6 7 9", 4)
        )

    class Parser(val input: Input) {
        fun reports(dampenerEnabled: Boolean = false): List<Report> {
            return input.lines
                .map { it.split(" ").map { it.toInt() } }
                .map { Report(it, dampenerEnabled) }
        }
    }

    class Report(val levels: List<Int>, val dampenerEnabled: Boolean = false) {
        fun isSafe(): Boolean {
            val rule1 = isAllIncreasing() || isAllDecreasing()
            val rule2 = hasGradualDeltas()

            if (rule1 && rule2) {
                return true
            }

            if (dampenerEnabled) {
                val alts = levels.indices.map { dropping ->
                    val newLevels = ArrayList(levels)
                    newLevels.removeAt(dropping)
                    newLevels
                }

                val newReports = alts.map { Report(it) }

                return newReports.any { it.isSafe() }
            }

            return false
        }

        fun isAllIncreasing(): Boolean {
            return levels.equals(levels.sorted())
        }

        fun isAllDecreasing(): Boolean {
            return levels.equals(levels.sortedDescending())
        }

        fun hasGradualDeltas(): Boolean {
            return levels
                .mapIndexedNotNull { index, level ->
                    val nextValue = levels.getOrNull(index + 1)
                    val delta = nextValue?.let { abs(it - level) }
                    delta
                }
                .all { delta ->
                    delta in 1..3
                }
        }
    }
}

fun main() {
    val p = Puzzle2402()
    val text = object {}.javaClass.getResource("/Puzzle2402.txt")?.readText()
    p.run(text!!)
}