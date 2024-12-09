package puzzles

import jdk.jshell.Diag
import utils.*

class Puzzle2408: Puzzle() {
    override fun solveA(input: Input): Any {
        return Diagram(input).allAntinodes().count()
    }

    override val testCasesA: List<TestCase>
        get() = listOf(
            TestCase("............\n" +
                    "........0...\n" +
                    ".....0......\n" +
                    ".......0....\n" +
                    "....0.......\n" +
                    "......A.....\n" +
                    "............\n" +
                    "............\n" +
                    "........A...\n" +
                    ".........A..\n" +
                    "............\n" +
                    "............", 14)
        )

    override fun solveB(input: Input): Any {
        return Diagram2(input).allAntinodes().count()
    }

    override val testCasesB: List<TestCase>
        get() = listOf(
            TestCase("............\n" +
                    "........0...\n" +
                    ".....0......\n" +
                    ".......0....\n" +
                    "....0.......\n" +
                    "......A.....\n" +
                    "............\n" +
                    "............\n" +
                    "........A...\n" +
                    ".........A..\n" +
                    "............\n" +
                    "............", 34)
        )
    open class Diagram(val input: Input) {
        val boundingRect: GridRect = GridRect.enclosing(input.grid.keys)

        fun getAllAntennas(): Map<GridPoint, Char> {
            return input.grid.filter { it.value != '.' }
        }

        fun getAntennaLocations(frequency: Char): Set<GridPoint> {
            return HashSet(getAllAntennas()
                .filter { it.value == frequency }
                .map { it.key })
        }

        fun getAllAntinodes(frequency: Char): Set<GridPoint> {
            val antinodeSet = HashSet<GridPoint>()
            val allLocations = getAntennaLocations(frequency)
            for (a in allLocations) {
                for (b in allLocations) {
                    antinodeSet.addAll(getAntinodes(a, b))
                }
            }
            return antinodeSet
        }

        open fun getAntinodes(antenna1: GridPoint, antenna2: GridPoint): Set<GridPoint> {
            if (antenna1 == antenna2) {
                // empty set
                return setOf()
            }

            val vec1 = antenna1.vectorTo(antenna2)
            val vec1p = vec1 * -1
            val node1 = antenna1 + vec1p

            val vec2 = antenna2.vectorTo(antenna1)
            val vec2p = vec2 * -1
            val node2 = antenna2 + vec2p

            return setOf(node1, node2)
        }

        fun allAntinodes(): Set<GridPoint> {
            val frequencies = HashSet(getAllAntennas().map { it.value })
            return frequencies
                .flatMap { getAllAntinodes(it) }
                .filter { boundingRect.contains(it) }
                .toSet()
        }
    }

    class Diagram2(input: Input) : Diagram(input) {
        override fun getAntinodes(antenna1: GridPoint, antenna2: GridPoint): Set<GridPoint> {
            if (antenna1 == antenna2) {
                // empty set
                return setOf(antenna1)
            }

            val set = HashSet<GridPoint>()
            set.add(antenna1)
            set.add(antenna2)

            val vec1 = antenna1.vectorTo(antenna2)
            val vec1p = vec1 * -1
            var point1 = antenna1 + vec1p
            while (boundingRect.contains(point1)) {
                set.add(point1)
                point1 += vec1p
            }

            val vec2 = antenna2.vectorTo(antenna1)
            val vec2p = vec2 * -1
            var point2 = antenna2 + vec2p
            while (boundingRect.contains(point2)) {
                set.add(point2)
                point2 += vec2p
            }

            return set
        }
    }
}

fun main() {
    val p = Puzzle2408()
    val text = object {}.javaClass.getResource("/Puzzle2408.txt")?.readText()
    p.run(text!!)
}