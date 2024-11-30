package puzzles

import utils.*

class Puzzle2405: Puzzle() {
    override fun solveA(input: Input): Any {
        return super.solveA(input)
    }

    override val testCasesA: List<TestCase>
        get() = super.testCasesA

    override fun solveB(input: Input): Any {
        return super.solveB(input)
    }

    override val testCasesB: List<TestCase>
        get() = super.testCasesB
}

fun main() {
    val p = Puzzle2405()
    val text = object {}.javaClass.getResource("/.txt")?.readText()
    p.run(text!!)
}