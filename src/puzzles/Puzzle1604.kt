package puzzles

import utils.*

class Puzzle1604: Puzzle() {
    override fun solveA(input: Input): Any {
        val rooms = input.lines.map { l -> parseLine(l) }
        return rooms.filter { r -> r.isValid() }
            .map { r -> r.sectorID }
            .sum()
    }

    override val testCasesA: List<TestCase>
        get() = listOf(
            TestCase("aaaaa-bbb-z-y-x-123[abxyz]", 123),
            TestCase("a-b-c-d-e-f-g-h-987[abcde]", 987),
            TestCase("not-a-real-room-404[oarel]", 404),
            TestCase("totally-real-room-200[decoy]", 0),
        )

    override fun solveB(input: Input): Any {
        val rooms = input.lines.map { l -> parseLine(l) }
        rooms.filter { r -> r.isValid() }
            .map { r -> "${r.sectorID}: ${r.decryptedName()}" }
            .forEach { n -> println(n) }

        return 0
    }

    fun parseLine(line: String): EncryptedRoom {
        val regex = Regex("([-a-z]+)-(\\d+)\\[([a-z]{5})\\]")
        val match = regex.find(line)
        val values = match!!.groupValues

        return EncryptedRoom(values[1], values[2].toInt(), values[3])
    }

    class EncryptedRoom(val name: String, val sectorID: Int, val checksum: String) {
        fun isValid(): Boolean {
            val chars = Frequency(name.toList())
            chars['-'] = 0

            val expectedChecksum = chars.mostFrequent()
                .sortedByDescending { entry ->
                    val alphabet = "abcdefghijklmnopqrstuvwxyz"
                    val firstIndex = alphabet.indexOf(entry.first)

                    // The frequency is the first priority when sorting.
                    // The index of the character breaks the tie.
                    (entry.second * 100) - firstIndex
                }
                .map { p -> p.first }
                .take(5)
                .joinToString("")

            return expectedChecksum == checksum
        }

        fun decryptedName(): String {
            return name.map { c -> decryptChar(c) }
                .joinToString("")
        }

        fun decryptChar(char: Char): Char {
            if (char == '-') {
                return ' '
            }

            val alphabet = "abcdefghijklmnopqrstuvwxyz"
            val index = alphabet.indexOf(char)
            val newIndex = (index + sectorID) % alphabet.count()
            return alphabet[newIndex]
        }
    }
}

fun main() {
    val p = Puzzle1604()
    val text = object {}.javaClass.getResource("/Puzzle1604.txt")?.readText()
    p.run(text!!)
}