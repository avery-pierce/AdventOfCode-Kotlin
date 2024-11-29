package puzzles

import GridPoint
import GridVector
import Input
import Puzzle
import TestCase

class Puzzle1602: Puzzle() {
    override fun solveA(input: Input): Any {
        val mover = Mover(input.lines)
        return mover.getCombination()
    }

    override val testCasesA: List<TestCase>
        get() = listOf(
            TestCase("ULL\n" +
                    "RRDDD\n" +
                    "LURDL\n" +
                    "UUUUD", "1985")
        )

    override fun solveB(input: Input): Any {
        val mover = Mover(input.lines)
        mover.keypad = Mover.keypad2
        return mover.getCombination()
    }

    override val testCasesB: List<TestCase>
        get() = listOf(
            TestCase("ULL\n" +
                    "RRDDD\n" +
                    "LURDL\n" +
                    "UUUUD", "5DB3")
        )

    class Mover(val lines: List<String>) {
        companion object {
            val keypad1 = mapOf(
                GridPoint(0, 2) to "1",
                GridPoint(1, 2) to "2",
                GridPoint(2, 2) to "3",
                GridPoint(0, 1) to "4",
                GridPoint(1, 1) to "5",
                GridPoint(2, 1) to "6",
                GridPoint(0, 0) to "7",
                GridPoint(1, 0) to "8",
                GridPoint(2, 0) to "9"
            )

            val keypad2 = mapOf(
                GridPoint(2, 4) to "1",

                GridPoint(1, 3) to "2",
                GridPoint(2, 3) to "3",
                GridPoint(3, 3) to "4",

                GridPoint(0, 2) to "5",
                GridPoint(1, 2) to "6",
                GridPoint(2, 2) to "7",
                GridPoint(3, 2) to "8",
                GridPoint(4, 2) to "9",

                GridPoint(1, 1) to "A",
                GridPoint(2, 1) to "B",
                GridPoint(3, 1) to "C",

                GridPoint(2, 0) to "D"
            )
        }

        var keypad = keypad1

        var location = GridPoint(1, 1) // Start at the middle

        fun readHere(): String {
            return keypad.get(location) ?: "0"
        }

        fun getCombination(): String {
            var combination = ""
            for (line in lines) {
                move(line)
                combination += readHere()
            }
            return combination
        }

        fun move(line: String) {
            for (char in line) {
                move(char)
            }
        }

        fun move(char: Char) {
            val previous = location

            if (char == 'U') {
                location += GridVector.north
            } else if (char == 'D') {
                location += GridVector.south
            } else if (char == 'L') {
                location += GridVector.west
            } else if (char == 'R') {
                location += GridVector.east
            }

            if (keypad.get(location) == null) {
                // move back
                location = previous
            }
        }
    }
}

fun main() {
    val p = Puzzle1602()
    p.run("RRLLRLLRULLRUUUDRDLDDLLLDDDDDUUURRRRUUDLRULURRRDRUDRUUDDRUDLLLRLDDDUDRDDRRLLLLRLRLULUURDRURRUULDRRDUDURRUURURDLURULLDUDRDLUUUUDDURRLLLUDLDLRDRRRDULLDLDULLDRLDLDURDLRRULLDDLDRLLLUDDLLRDURULLDDDDDUURURLRLRRDUURUULRLLLULLRLULLUUDRRLLDURLDDDDULUUDLUDDDULRLDURDDRUUDRRUUURLLLULURUDRULDRDUDUDRRDDULRURLLRRLRRLLDLULURDRDRULDRDRURUDLLRRDUUULDDDUURDLULDLRLLURRURLLUDURDDRUDRDLLLLDLRLDLDDRDRRDUUULLUULRRDLURLDULLDLDUUUULLLDRURLRULLULRLULUURLLRDDRULDULRLDRRURLURUDLRRRLUDLDUULULLURLDDUDDLLUDRUDRLDUDURRRRLRUUURLUDDUDURDUDDDLLRLRDDURDRUUDUDRULURLRLDRULDRRLRLDDDRDDDRLDUDRLULDLUDLRLRRRLRDULDDLRRDDLDDULDLLDU\n" +
            "RULLUDDUDLULRRDLLDRUDLLLDURLLLURDURLRDRRDLRDRDLLURRULUULUDUDDLLRRULLURDRLDURDLDDUURLUURLDLDLRLDRLRUULDRLRLDRLRLUDULURDULLLDRUDULDURURRRUDURDUDLRDRRURULRRLRLRRRRRRDRUDLDRULDRUDLRDLRRUDULDLRLURRRLLDRULULRUDULRLULLRLULDRUDUULLRUULDULDUDDUUULLLDRDDRRDLURUUDRRLRRRDLRRLULLLLDLRUULDLLULURUURURDRURLLDUDRRURRURRUUDDRRDDRRRRUDULULRLUULRRDDRDDLLUDLDLULLRLDRLLUULDURLDRULDDUDRUUUURRLDDUDRUURUDLLDLDLURDLULDRLLLULLLUDLLDLD\n" +
            "RDLDULURDLULRRDLRLLLULRUULURULLLDLLDDRLLURUUUURDRLURLLRLRLLLULRDLURDURULULDDUDDUDRLRLDLULLURRRUULUDRDURRRUDDDLUDLDLRLRRLLLRUULLLLURRDDDRRRUURULRLDRRRLRLUDDRRULDDDRUUDDRLLDULRLUDUDLDLDDDUDDLLDDRDRDUDULDRRUDRDRRDRLUURDLRDDDULLDRRRRRUDRLURDUURRDDRLUDLURRRLRDDDLRRLUULRLURDUUURRDLDDULLLRURRRUDRLUDLLDDDDDUDDRDULLUUDDURRLULLUDULUUDRLDRRRLLURLRRLLDLLLLUDRUUUDDULLRDLLDUDUDUURRUUUDRUURDRDLLDLDDULLDDRRULDLDDUUURLDLULLLRRLLRDDULLDLDLDDLDLDULURRDURURDRDRRDLR\n" +
            "RDRLRRUUDRLDUDLLDLUDLUUDUDLRRUUDRDDDLDDLLLRRRUDULLRRRRRURRRLUDDDLRRRRUUULDURDRULLDLRURRUULUDRURRRRLRURLRDUUDUDUDRDDURRURUDLLLLLRURUULRUURLLURDRUURLUDDDRLDDURDLDUDRURDRLRRRRUURDDRRRRURDLUUDRLDRDUULURUDDULLURRDUDLUULLDURRURLUDUUDRDDDUUDDUUUULDLDUDDLUDUUDRURLLULRUUULLRRDDUDDLULDDUUUDLUDDLDDLLRUUDRULLRRDRLLDLLRRLULLRRDDRLRDUULLLUULLDLLUDUDDLRDULUDLDLUDDRRRRDUDLUULLULDLRRDLULRLRRRULRURRDRLULDDUDLDLDULLURLLRDLURRULURDLURLUDRDRRUUDRLLUDDRLRDDUURLRRDUDLDRURDUUUDRRLLRDLDLLDRRURLUDURUULDUDLDDDDRUULLDDRLRURRDURLURRLDDRRRRLRLRDRURUDDRDLDRURLULDDL\n" +
            "RULRDLDDLRURDDDDDDRURLLLDDDUUULLRRDLDLURUURLUDLURRLUDUURDULDRUULDDURULDUULDDULLLUDLRULDRLDLRDDRRDLDDLLDRRUDDUDRDUULUDLLLDDLUUULDDUUULRRDULLURLULDLRLLLRLURLLRLRLDRDURRDUUDDURRULDDURRULRDRDUDLRRDRLDULULDRDURDURLLLDRDRLULRDUURRUUDURRDRLUDDRRLDLDLULRLLRRUUUDDULURRDRLLDLRRLDRLLLLRRDRRDDLDUULRLRRULURLDRLRDULUDRDLRUUDDDURUDLRLDRRUDURDDLLLUDLRLURDUDUDULRURRDLLURLLRRRUDLRRRLUDURDDDDRRDLDDLLDLRDRDDRLLLURDDRDRLRULDDRRLUURDURDLLDRRRDDURUDLDRRDRUUDDDLUDULRUUUUDRLDDD")
}