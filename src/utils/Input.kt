package utils

import java.util.Dictionary

class Input(val text: String) {
    val lines: List<String>
        get() {
            return text.split("\n")
        }

    val grid: Map<GridPoint, Char>
        get() {
            val map = HashMap<GridPoint, Char>()

            for (y in lines.indices) {
                val line = lines[y]

                for (x in line.indices) {
                    val char = line[x]
                    val point = GridPoint(x, y)

                    map[point] = char
                }
            }

            return map
        }
}