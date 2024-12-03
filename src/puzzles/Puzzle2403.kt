package puzzles

import utils.*

class Puzzle2403: Puzzle() {
    override fun solveA(input: Input): Any {
        return parse(input)
            .map { it.result() }
            .sum()
    }

    override val testCasesA: List<TestCase>
        get() = listOf(
            TestCase("xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))", 161)
        )

    override fun solveB(input: Input): Any {
        val muls = parse(input)
        val dos = parseDos(input)
        return Instructions(muls, dos).computeSum()
    }

    override val testCasesB: List<TestCase>
        get() = listOf(
            TestCase("xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))", 48)
        )

    fun parse(input: Input): Sequence<Multiply> {
        val regex = Regex("mul\\((\\d{1,3}),(\\d{1,3})\\)")
        val all = regex.findAll(input.text)

        return all.map {
            val l = it.groupValues[1].toInt()
            val r = it.groupValues[2].toInt()
            val start = it.range.first
            Multiply(start, l, r)
        }
    }

    fun parseDos(input: Input): Sequence<Dos> {
        val regex = Regex("do\\(\\)|don't\\(\\)")
        val all = regex.findAll(input.text)

        return all.map {
            val enables = it.value == "do()"
            val start = it.range.first
            Dos(start, enables)
        }
    }

    class Instructions(val multiplies: Sequence<Multiply>, val dos: Sequence<Dos>) {
        fun computeSum(): Int {
            return multiplies
                .filter { isEnabledAtIndex(it.index) }
                .map {
                    it.result()
                }
                .sum()
        }

        fun isEnabledAtIndex(index: Int): Boolean {
            val applies = dos.filter { it.index < index }
            if (applies.count() == 0) {
                return true
            } else {
                return applies.last().enables
            }
        }
    }

    class Dos(val index: Int, val enables: Boolean)

    class Multiply(val index: Int, val l: Int, val r: Int) {
        fun result(): Int {
           return l * r
        }
    }
}

fun main() {
    val p = Puzzle2403()
    val text = object {}.javaClass.getResource("/Puzzle2403.txt")?.readText()
    p.run(text!!)
}