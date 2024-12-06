package puzzles

import utils.*

class Puzzle2406: Puzzle() {
    override fun solveA(input: Input): Any {
        val w = parseMap(input)
        w.run()
        return w.trail.count()

        // 5460 -- Too low
    }

    override val testCasesA: List<TestCase>
        get() = listOf(
            TestCase("....#.....\n" +
                    ".........#\n" +
                    "..........\n" +
                    "..#.......\n" +
                    ".......#..\n" +
                    "..........\n" +
                    ".#..^.....\n" +
                    "........#.\n" +
                    "#.........\n" +
                    "......#...", 41)
        )

    override fun solveB(input: Input): Any {
        val w1 = parseMap(input)
        w1.run()

        return w1.boxOptions()
            .map { w1.blockers.union(setOf(it)) }
            .map { Warehouse(w1.grid, it, w1.startPoint) }
            .filter {
                it.run()
                it.isLooping()
            }
            .count()
    }

    override val testCasesB: List<TestCase>
        get() = listOf(
            TestCase("....#.....\n" +
                    ".........#\n" +
                    "..........\n" +
                    "..#.......\n" +
                    ".......#..\n" +
                    "..........\n" +
                    ".#..^.....\n" +
                    "........#.\n" +
                    "#.........\n" +
                    "......#...", 6)
        )

    fun parseMap(input: Input): Warehouse {
        val g = input.grid
        val rect = GridRect.enclosing(g.keys)

        val blockers =  HashSet(g.filter { it.value == '#' }.map { it.key })
        val walkerLocation = g.filter { it.value == '^' }.map { it.key }.first()
        return Warehouse(rect, blockers, walkerLocation)
    }

    class Walker(var location: GridPoint, var direction: GridVector) {
        fun moveForward() {
            location = location + direction
        }

        fun turnRight() {
            direction = direction.rotatedClockwise()
        }
    }

    class Warehouse(val grid: GridRect, val blockers: Set<GridPoint>, val startPoint: GridPoint) {
        val trail = HashSet<GridPoint>()
        var trail2 = HashSet<Pair<GridPoint, GridVector>>()
        val walker = Walker(startPoint, GridVector.north)

        fun markAndMove() {
            trail.add(walker.location)
            trail2.add(Pair(walker.location, walker.direction))

            if (isBlockerAhead()) {
                walker.turnRight()
            } else {
                walker.moveForward()
            }
        }

        fun isBlockerAhead(): Boolean {
            val coord = walker.location + walker.direction
            return blockers.contains(coord)
        }

        fun isLooping(): Boolean {
            return trail2.contains(Pair(walker.location, walker.direction))
        }

        fun isInBounds(): Boolean {
            return grid.contains(walker.location)
        }

        fun run() {
            while (isInBounds() && !isLooping()) {
                markAndMove()
            }
        }

        fun boxOptions(): Set<GridPoint> {
            return HashSet(trail.filter { it != startPoint })
        }
    }
}

fun main() {
    val p = Puzzle2406()
    val text = object {}.javaClass.getResource("/Puzzle2406.txt")?.readText()
    p.run(text!!)
}