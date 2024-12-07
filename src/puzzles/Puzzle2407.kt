package puzzles

import jdk.incubator.vector.VectorOperators.Test
import utils.*
import java.math.BigInteger

class Puzzle2407: Puzzle() {
    override fun solveA(input: Input): Any {
        return input.lines
            .map {
                val pairs = it.split(":")
                val args = pairs[1].split(" ").mapNotNull { it.toBigIntegerOrNull() }
                TestLine(pairs[0].toBigInteger(), args, setOf(Operator.ADD, Operator.MULTIPLY))
            }
            .filter { it.isValid() }
            .map { it.output }
            .reduce { acc, bigInteger -> acc + bigInteger }
    }

    override val testCasesA: List<TestCase>
        get() = listOf(
            TestCase("190: 10 19\n" +
                    "3267: 81 40 27\n" +
                    "83: 17 5\n" +
                    "156: 15 6\n" +
                    "7290: 6 8 6 15\n" +
                    "161011: 16 10 13\n" +
                    "192: 17 8 14\n" +
                    "21037: 9 7 18 13\n" +
                    "292: 11 6 16 20", 3749)
        )

    override fun solveB(input: Input): Any {
        return input.lines
            .map {
                val pairs = it.split(":")
                val args = pairs[1].split(" ").mapNotNull { it.toBigIntegerOrNull() }
                TestLine(pairs[0].toBigInteger(), args, setOf(Operator.ADD, Operator.MULTIPLY, Operator.CONCAT))
            }
            .filter { it.isValid() }
            .map { it.output }
            .reduce { acc, bigInteger -> acc + bigInteger }
    }

    override val testCasesB: List<TestCase>
        get() = listOf(
            TestCase("190: 10 19\n" +
                    "3267: 81 40 27\n" +
                    "83: 17 5\n" +
                    "156: 15 6\n" +
                    "7290: 6 8 6 15\n" +
                    "161011: 16 10 13\n" +
                    "192: 17 8 14\n" +
                    "21037: 9 7 18 13\n" +
                    "292: 11 6 16 20", 11387)
        )

    enum class Operator {
        ADD,
        MULTIPLY,
        CONCAT,
    }

    class TestLine(val output: BigInteger, val args: List<BigInteger>, val operators: Set<Operator>) {
        fun isValid(): Boolean {
            val mutableArgs = ArrayList(args)
            val first2 = mutableArgs.take(2)
            mutableArgs.removeFirst()
            mutableArgs.removeFirst()

            val outputsOfFirst2 = Calculator(first2[0], first2[1]).allPossibleResults(operators)

            if (mutableArgs.isEmpty()) {
                // If there are only 2 elements, we can simply use a calculator
                return outputsOfFirst2.contains(output)
            } else {
                // Otherwise, reduce the first two elements into a map of their possible values
                val newArgs = outputsOfFirst2
                    .map {
                        val newList = arrayListOf(it)
                        newList.addAll(mutableArgs)
                        TestLine(output, newList, operators)
                    }

                return newArgs.any { it.isValid() }
            }
        }
    }

    class Calculator(val left: BigInteger, val right: BigInteger) {
        fun allPossibleResults(operators: Set<Operator>): Set<BigInteger> {
            return HashSet(operators.map { evaluate(it) })
        }

        fun evaluate(operator: Operator): BigInteger {
            when (operator) {
                Operator.ADD -> return left + right
                Operator.MULTIPLY -> return left * right
                Operator.CONCAT -> return (left.toString() + right.toString()).toBigInteger()
            }
        }
    }
}

fun main() {
    val p = Puzzle2407()
    val text = object {}.javaClass.getResource("/Puzzle2407.txt")?.readText()
    p.run(text!!)
}