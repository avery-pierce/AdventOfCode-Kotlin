package puzzles

import java.security.MessageDigest

import utils.*

class Puzzle1605: Puzzle() {
    override fun solveA(input: Input): Any {
        val decoder = Decoder(input.text)
        return decoder.getPassword()
    }

    override val testCasesA: List<TestCase>
        get() = listOf(
            TestCase("abc", "18f47a30")
        )

    override fun solveB(input: Input): Any {
        val decoder = Decoder(input.text)
        return decoder.getPassword2()
    }

    override val testCasesB: List<TestCase>
        get() = listOf(
            TestCase("abc", "05ace8e3")
        )

    class Decoder(val doorID: String) {
        fun getPassword(): String {
            var index = 0
            var password = ""

            while (password.length < 8) {
                val checkVal = "$doorID$index"
                val hash = md5(checkVal)
                if (hash.startsWith("00000")) {
                    println("$checkVal - $hash")
                    password += hash[5]
                }

                index += 1
            }

            return password.lowercase()
        }

        fun getPassword2(): String {
            var iteration = 0
            val password = ArrayList<Char>("________".toList())
            val passwordLength = password.count()

            while (password.contains('_')) {
                val checkVal = "$doorID$iteration"
                val hash = md5(checkVal)

                if (hash.startsWith("00000")) {
                    println("$checkVal - $hash")
                    val indexChar = hash[5]
                    val char = hash[6]

                    try {
                        val index = indexChar.toString().toInt()
                        if (index < passwordLength && password[index] == '_') {
                            password[index] = char
                            println(password.joinToString(""))
                        }
                    } catch (e: NumberFormatException) {
                        // Do nothing.
                    }
                }

                iteration += 1
            }

            return password.joinToString("").lowercase()
        }

        fun md5(input: String): String {
            val bytes = MessageDigest.getInstance("MD5").digest(input.toByteArray())
            return bytes.joinToString("") { "%02x".format(it) }
        }
    }
}

fun main() {
    val p = Puzzle1605()
    p.runB(Input("cxdnnyjw"))
}