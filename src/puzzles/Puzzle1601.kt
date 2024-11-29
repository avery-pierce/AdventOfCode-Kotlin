package puzzles

import GridPoint
import GridVector
import Input
import Puzzle
import TestCase

class Puzzle1601: Puzzle() {

    override fun solveA(input: Input): String {
        val mover = Mover()
        val rules = input.text.split(", ")

        rules.forEach { command ->
            mover.move(command)
        }

        val answer = mover.location.manhattanDistanceTo(GridPoint.zero)

        return "$answer"
    }

    override val testCasesA: List<TestCase>
        get() = listOf(
            TestCase("R2, L3", 5),
            TestCase("R2, R2, R2", 2),
            TestCase("R5, L5, R5, R3", 12),
        )

    override fun solveB(input: Input): String {
        val mover = Mover2()
        val rules = input.text.split(", ")

        rules.forEach { command ->
            mover.move(command)
        }

        val answer = mover.location.manhattanDistanceTo(GridPoint.zero)

        return "$answer"
    }

    override val testCasesB: List<TestCase>
        get() = listOf(
            TestCase("R8, R4, R4, R8", 4)
        )

    open class Mover {
        var direction = GridVector.north
        var location = GridPoint.zero
        var visitedLocations = mutableSetOf<GridPoint>()

        open fun move(command: String) {
            if (command.startsWith("R")) {
                direction = direction.rotatedClockwise()
            } else if (command.startsWith("L")) {
                direction = direction.rotatedCounterClockwise()
            }

            val distance = command.drop(1).toInt()
            location += (direction * distance)
        }

        val hasBeenHereBefore: Boolean get() {
            return visitedLocations.contains(location)
        }

        fun markAsVisted() {
            visitedLocations.add(location)
        }
    }

    class Mover2: Mover() {
        override fun move(command: String) {
            if (command.startsWith("R")) {
                direction = direction.rotatedClockwise()
            } else if (command.startsWith("L")) {
                direction = direction.rotatedCounterClockwise()
            }

            var distance = command.drop(1).toInt()
            while (distance > 0) {
                if (hasBeenHereBefore) {
                    break
                } else {
                    markAsVisted()
                }

                location += direction
                distance -= 1
            }
        }
    }
}

fun main() {
    val p = Puzzle1601()
    p.run("R3, L2, L2, R4, L1, R2, R3, R4, L2, R4, L2, L5, L1, R5, R2, R2, L1, R4, R1, L5, L3, R4, R3, R1, L1, L5, L4, L2, R5, L3, L4, R3, R1, L3, R1, L3, R3, L4, R2, R5, L190, R2, L3, R47, R4, L3, R78, L1, R3, R190, R4, L3, R4, R2, R5, R3, R4, R3, L1, L4, R3, L4, R1, L4, L5, R3, L3, L4, R1, R2, L4, L3, R3, R3, L2, L5, R1, L4, L1, R5, L5, R1, R5, L4, R2, L2, R1, L5, L4, R4, R4, R3, R2, R3, L1, R4, R5, L2, L5, L4, L1, R4, L4, R4, L4, R1, R5, L1, R1, L5, R5, R1, R1, L3, L1, R4, L1, L4, L4, L3, R1, R4, R1, R1, R2, L5, L2, R4, L1, R3, L5, L2, R5, L4, R5, L5, R3, R4, L3, L3, L2, R2, L5, L5, R3, R4, R3, R4, R3, R1")
}