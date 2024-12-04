package puzzles

import utils.*

class Puzzle2404: Puzzle() {
    override fun solveA(input: Input): Any {
        return WordSearch(input.grid).countXmas()
    }

    override val testCasesA: List<TestCase>
        get() = listOf(
            TestCase("MMMSXXMASM\n" +
                    "MSAMXMSMSA\n" +
                    "AMXSXMAAMM\n" +
                    "MSAMASMSMX\n" +
                    "XMASAMXAMM\n" +
                    "XXAMMXXAMA\n" +
                    "SMSMSASXSS\n" +
                    "SAXAMASAAA\n" +
                    "MAMMMXMMMM\n" +
                    "MXMXAXMASX", 18)
        )

    override fun solveB(input: Input): Any {
        return WordSearch(input.grid).countMasCross()
    }

    override val testCasesB: List<TestCase>
        get() = listOf(
            TestCase("MMMSXXMASM\n" +
                    "MSAMXMSMSA\n" +
                    "AMXSXMAAMM\n" +
                    "MSAMASMSMX\n" +
                    "XMASAMXAMM\n" +
                    "XXAMMXXAMA\n" +
                    "SMSMSASXSS\n" +
                    "SAXAMASAAA\n" +
                    "MAMMMXMMMM\n" +
                    "MXMXAXMASX", 9)
        )

    class WordSearch(val chars: Map<GridPoint, Char>) {

        fun read(startPoint: GridPoint, direction: GridVector): String {
            val length = 4
            var string = ""

            var location = startPoint
            while (string.length < length) {
                val char = chars[location] ?: return string
                string += char
                location += direction
            }
            return string
        }

        fun countXmas(): Int {
            val directions = listOf(
                GridVector.north + GridVector.west,
                GridVector.north,
                GridVector.north + GridVector.east,
                GridVector.east,
                GridVector.south + GridVector.east,
                GridVector.south,
                GridVector.south + GridVector.west,
                GridVector.west
            )

            return chars
                .filter { it.value == 'X' }
                .flatMap {
                    val start = it.key
                    directions.map { read(start, it) }
                }
                .filter { it == "XMAS" }
                .count()
        }

        fun countMasCross(): Int {
            return chars
                .filter { it.value == 'A' }
                .filter { checkCross(it.key) }
                .count()
        }

        fun checkCross(coord: GridPoint): Boolean {
            val a = listOf(
                coord + GridVector.north + GridVector.west,
                coord,
                coord + GridVector.south + GridVector.east,
            )

            val b = listOf(
                coord + GridVector.north + GridVector.east,
                coord,
                coord + GridVector.south + GridVector.west,
            )

            val ra = read(a)
            val rb = read(b)
            return (ra == "MAS" || ra == "SAM") && (rb == "MAS" || rb == "SAM")
        }

        fun read(coords: List<GridPoint>): String {
            return coords.mapNotNull { chars[it] }.joinToString("")
        }
    }
}

fun main() {
    val p = Puzzle2404()
    val text = object {}.javaClass.getResource("/Puzzle2404.txt")?.readText()
    p.run(text!!)
}