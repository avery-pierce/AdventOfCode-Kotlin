package puzzles

import jdk.incubator.vector.VectorOperators.Test
import utils.*
import java.math.BigInteger
import kotlin.math.*

class Puzzle2409: Puzzle() {
    override fun solveA(input: Input): Any {
        val disk = Disk(input)
        val mem = disk.expandedFiles()
        val compressed = disk.compress(mem)
        return disk.checksum(compressed)

        // 6401092019345
    }

    override val testCasesA: List<TestCase>
        get() = listOf(
            TestCase("2333133121414131402", 1928)
        )

    override fun solveB(input: Input): Any {
        val disk = Disk2(input)
        val files = disk.getFiles()
        val compressed = disk.compress(files)

        println(compressed.map { it.description })

        return disk.checksum(compressed)
        // 4075252874194 - too low!
        // 6431472344710
        // 6431873025282 - too high!
        // 6431873025282
        // 6431873025282
        // 6434162701791 - too high!
    }

    override val testCasesB: List<TestCase>
        get() = listOf(
            TestCase("2333133121414131402", 2858)
        )

    class Disk(val input: Input) {
        fun getRanges(): List<Range> {
            val ranges = ArrayList<Range>()

            var isData = true
            var fileId = 0
            var fileOffset = 0
            for (char in input.text) {
                val length = char.digitToInt()

                if (isData) {
                    val range = Range(fileId, fileOffset, length)
                    ranges.add(range)
                    fileId += 1
                } else {
                    val range = Range(-1, fileOffset, length)
                    ranges.add(range)
                }

                fileOffset += length
                isData = !isData
            }

            return ranges
        }

        fun expandedFiles(ranges: List<Range> = getRanges()): List<Int> {
            val memory = ArrayList<Int>()

            for (range in ranges) {
                while (memory.count() < (range.start + range.length)) {
                    memory.add(range.fileId)
                }
            }

            return memory
        }

        fun compress(files: List<Int>): List<Int> {
            val mutable = ArrayList(files)

            while (true) {
                val firstOpenIndex = mutable.indexOf(-1)
                if (firstOpenIndex == -1) {
                    break
                } else {
                    val lastBlock = mutable.removeLast()
                    if (lastBlock == -1) {
                        continue
                    }

                    if (firstOpenIndex >= mutable.count()) {
                        mutable.add(lastBlock)
                    } else {
                        mutable.set(firstOpenIndex, lastBlock)
                    }
                }
            }

            return mutable
        }

        fun compressWholeFiles(): List<Range> {
            val ranges = getRanges()
            var mutable = ArrayList<Range>(ranges)

            for (i in ranges.indices.reversed()) {
                val movingRange = ranges[i]
                if (movingRange.fileId == -1) {
                    continue
                }

                for (j in mutable.indices) {
                    val openRange = mutable[j]
                    if (openRange.fileId != -1) {
                        continue
                    }

                    if (openRange.length < movingRange.length) {
                        continue
                    }

                    if (i < j) {
                        continue
                    }

                    // Replace the gap with an empty range
                    val emptyRange = Range(-1, movingRange.start, movingRange.length)
                    val emptyIndex = mutable.indexOf(movingRange)
                    mutable[emptyIndex] = emptyRange

                    val movedRange = Range(movingRange.fileId, openRange.start, movingRange.length)
                    mutable[j] = movedRange

                    // Create a new empty range for the remainder
                    val remaining = openRange.length - movedRange.length
                    if (remaining > 0) {
                        val remainderRange = Range(-1, movedRange.start + movedRange.length, remaining)
                        mutable.add(j + 1, remainderRange)
                    }

                    break
                }

                val healed = ArrayList<Range>()
                var lastRange = Range(0, 0, 0)
                for (k in mutable) {
                    if (k.length == 0) {
                        continue
                    } else if (k.fileId == lastRange.fileId) {
                        lastRange.length += k.length
                    } else {
                        healed.add(lastRange)
                        lastRange = k
                    }
                }
                healed.add(lastRange)
                mutable = healed
            }

            return mutable
        }

        fun checksum(files: List<Int>): BigInteger {
            val zero = 0
            var accumulator = zero.toBigInteger()

            val zeroedMem = files.map { max(it, 0) }

            for (i in zeroedMem.indices) {
                val file = zeroedMem[i]
                accumulator += (i.toBigInteger() * file.toBigInteger())
            }
            return accumulator
        }
    }

    class Disk2(val input: Input) {
        fun getFiles(): List<Range> {
            val ranges = ArrayList<Range>()

            var isData = true
            var fileId = 0
            var fileOffset = 0
            for (char in input.text) {
                val length = char.digitToInt()

                if (isData) {
                    val range = Range(fileId, fileOffset, length)
                    ranges.add(range)
                    fileId += 1
                }

                fileOffset += length
                isData = !isData
            }

            return ranges
        }

        fun compress(files: List<Range>): List<Range> {
            val newList = ArrayList<Range>(files)

            for (i in files.indices.reversed()) {
                val fileToMove = files[i]

                val j = firstOpenRangeIndex(fileToMove.length, newList)
                println("File: ${fileToMove.description}; j = $j")

                j?.let {
                    val newStart = newList[it].end
                    if (newStart < fileToMove.start) {
                        newList.remove(fileToMove)
                        fileToMove.start = newStart
                        newList.add(it + 1, fileToMove)
                    }
                }

                println(newList.map { it.description })
            }

            return newList
        }

        fun firstOpenRangeIndex(n: Int, files: List<Range>): Int? {
            for (i in files.indices) {
                if (i + 1 >= files.count()) {
                    return null
                }

                val f = files[i]
                val next = files[i+1]
                val gap = next.start - f.end
                if (gap >= n) {
                    return i
                }
            }

            return null
        }

        fun checksum(range: Range): BigInteger {
            var accumulator = BigInteger("0")
            for (i in range.start..<range.end) {
                accumulator += (i * range.fileId).toBigInteger()
            }
            return accumulator
        }

        fun checksum(ranges: List<Range>): BigInteger {
            return ranges
                .map { checksum(it) }
                .reduce { acc, it -> acc + it }
        }
    }

    data class Range(var fileId: Int, var start: Int, var length: Int) {
        val description: String
            get() = "<${fileId}: ${start}~${length}>"

        val end: Int
            get() = start + length
    }
}

fun main() {
    val p = Puzzle2409()
    val text = object {}.javaClass.getResource("/Puzzle2409.txt")?.readText()
    p.run(text!!)
}