package puzzles

import utils.*
import kotlin.math.floor

class Puzzle2405: Puzzle() {
    override fun solveA(input: Input): Any {
        val updates = parseUpdates(input)
        val rules = parseRules(input)

        return updates.filter { it.isValid(rules) }
            .map { it.middleValue() }
            .sum()
    }

    override val testCasesA: List<TestCase>
        get() = listOf(TestCase("47|53\n" +
                "97|13\n" +
                "97|61\n" +
                "97|47\n" +
                "75|29\n" +
                "61|13\n" +
                "75|53\n" +
                "29|13\n" +
                "97|29\n" +
                "53|29\n" +
                "61|53\n" +
                "97|53\n" +
                "61|29\n" +
                "47|13\n" +
                "75|47\n" +
                "97|75\n" +
                "47|61\n" +
                "75|61\n" +
                "47|29\n" +
                "75|13\n" +
                "53|13\n" +
                "\n" +
                "75,47,61,53,29\n" +
                "97,61,53,29,13\n" +
                "75,29,13\n" +
                "75,97,47,61,53\n" +
                "61,13,29\n" +
                "97,13,75,29,47", 143))

    override fun solveB(input: Input): Any {
        val updates = parseUpdates(input)
        val rules = parseRules(input)

        return updates.filter { !it.isValid(rules) }
            .map { it.reorder(rules) }
            .map { it.middleValue() }
            .sum()
    }

    override val testCasesB: List<TestCase>
        get() = listOf(TestCase("47|53\n" +
                "97|13\n" +
                "97|61\n" +
                "97|47\n" +
                "75|29\n" +
                "61|13\n" +
                "75|53\n" +
                "29|13\n" +
                "97|29\n" +
                "53|29\n" +
                "61|53\n" +
                "97|53\n" +
                "61|29\n" +
                "47|13\n" +
                "75|47\n" +
                "97|75\n" +
                "47|61\n" +
                "75|61\n" +
                "47|29\n" +
                "75|13\n" +
                "53|13\n" +
                "\n" +
                "75,47,61,53,29\n" +
                "97,61,53,29,13\n" +
                "75,29,13\n" +
                "75,97,47,61,53\n" +
                "61,13,29\n" +
                "97,13,75,29,47", 123))

    fun parseRules(input: Input): List<OrderRule> {
        return input.sections[0]
            .map { it.split("|") }
            .map { OrderRule(it[0].toInt(), it[1].toInt()) }
    }

    fun parseUpdates(input: Input): List<PrintUpdate> {
        return input.sections[1]
            .map { it.split(",").map { it.toInt() } }
            .map { PrintUpdate(it) }
    }

    class Solver(val rules: List<OrderRule>, val updates: List<PrintUpdate>) {
        fun updatesInOrder(): List<PrintUpdate> {
            return updates.filter { it.isValid(rules) }
        }
    }

    class OrderRule(val first: Int, val second: Int) {
        fun check(list: List<Int>): Boolean {
            val leftIndex = list.indexOf(first)
            if (leftIndex < 0) return true

            val rightIndex = list.indexOf(second)
            if (rightIndex < 0) return true

            return leftIndex < rightIndex
        }
    }

    class PrintUpdate(val order: List<Int>) {
        fun isValid(rules: List<OrderRule>): Boolean {
            return rules.all { it.check(order) }
        }

        fun reorder(rules: List<OrderRule>): PrintUpdate {
            val list = order.sortedWith { i1, i2 ->
                val rule = ruleAffecting(rules, i1, i2)
                if (rule == null) {
                    0
                } else if (rule.check(listOf(i1, i2))) {
                    1
                } else {
                    -1
                }
            }

            return PrintUpdate(list)
        }

        fun ruleAffecting(rules: List<OrderRule>, first: Int, second: Int): OrderRule? {
            return rules.find { (it.first == first && it.second == second) || (it.first == second && it.second == first) }
        }

        fun middleValue(): Int {
            val index = (order.count().toFloat() / 2).toInt()
            return order[index]
        }
    }
}

fun main() {
    val p = Puzzle2405()
    val text = object {}.javaClass.getResource("/Puzzle2405.txt")?.readText()
    p.run(text!!)
}
