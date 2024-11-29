package puzzles

import Puzzle
import TestCase
import Input

class Puzzle1603: Puzzle() {
    override fun solveA(input: Input): Any {
        val triangles = input.lines.map({ string -> Triangle.fromLine(string) })

        return triangles
            .filter { triangle -> triangle.isValid() }
            .count()
    }

    override val testCasesA: List<TestCase>
        get() = listOf(
            TestCase("5 10 25", 0),
            TestCase("3 4 5", 1)
        )

    override fun solveB(input: Input): Any {
        val trianglesByRow = input.lines.map({ string -> Triangle.fromLine(string) })

        var trianglesByColumn = ArrayList<Triangle>()
        var triangleGroup = ArrayList<Triangle>()

        for (triangle in trianglesByRow) {
            triangleGroup.add(triangle)

            if (triangleGroup.count() == 3) {
                val tri1 = Triangle(triangleGroup.map({ t -> t.side1 }))
                val tri2 = Triangle(triangleGroup.map({ t -> t.side2 }))
                val tri3 = Triangle(triangleGroup.map({ t -> t.side3 }))

                trianglesByColumn.add(tri1)
                trianglesByColumn.add(tri2)
                trianglesByColumn.add(tri3)

                triangleGroup = ArrayList()
            }
        }

        return trianglesByColumn
            .filter { triangle -> triangle.isValid() }
            .count()
    }

    class Triangle(val side1: Int, val side2: Int, val side3: Int) {

        constructor(list: List<Int>) : this(list[0], list[1], list[2])

        companion object {
            fun fromLine(line: String): Triangle {
                val numbers = line.split(" ")
                    .mapNotNull { number ->
                        number.toIntOrNull()
                    }

                return Triangle(numbers[0], numbers[1], numbers[2])
            }
        }

        fun isValid(): Boolean {
            val test1 = side1 + side2 > side3
            val test2 = side2 + side3 > side1
            val test3 = side1 + side3 > side2
            return test1 && test2 && test3
        }
    }
}

fun main() {
    val p = Puzzle1603()
    val text = object {}.javaClass.getResource("/Puzzle1603.txt")?.readText()
    p.run(text!!)
}