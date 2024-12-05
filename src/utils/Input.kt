package utils

import java.util.Dictionary

class Input(val text: String) {
    val lines: List<String>
        get() {
            return text.split("\n")
        }

    val sections: List<List<String>>
        get() {
            var currentSection = ArrayList<String>()
            var allSections = ArrayList<List<String>>()

            for (line in lines) {
                if (line.length == 0) {
                    allSections.add(currentSection)
                    currentSection = ArrayList<String>()
                } else {
                    currentSection.add(line)
                }
            }

            allSections.add(currentSection)
            return allSections
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