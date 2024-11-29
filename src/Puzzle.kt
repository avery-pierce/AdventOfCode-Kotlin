open class Puzzle {
    open fun solveA(input: Input): Any {
        return "unsolved"
    }

    open val testCasesA: List<TestCase> = listOf()

    open fun solveB(input: Input): Any {
        return "unsolved"
    }

    open val testCasesB: List<TestCase> = listOf()

    fun runA(input: Input) {
        if (evaluateTestsA()) {
            println("Solving A...")
            println("============")
            val result = solveA(input)
            println(result)
        }
    }

    /// Returns true when tests pass.
    fun evaluateTestsA(): Boolean {
        var anyFailed = false
        println("Testing A...")
        testCasesA.forEachIndexed() { index, testCase ->
            val result = solveA(testCase.input)
            val testNumber = index + 1
            print("Test $testNumber/${testCasesA.count()}... ")
            if (result.toString() == testCase.expectedResult.toString()) {
                println("Pass!")
            } else {
                println("Failed. Expected: ${testCase.expectedResult}, got: ${result}")
                anyFailed = true
            }
        }

        return !anyFailed
    }

    fun runB(input: Input) {
        if (evaluateTestsB()) {
            println("Solving B...")
            println("============")
            val result = solveB(input)
            println(result)
        }
    }

    /// Returns true when tests pass.
    fun evaluateTestsB(): Boolean {
        var anyFailed = false
        println("Testing B...")
        testCasesB.forEachIndexed() { index, testCase ->
            val result = solveB(testCase.input)
            val testNumber = index + 1
            print("Test $testNumber/${testCasesB.count()}... ")
            if (result.toString() == testCase.expectedResult.toString()) {
                println("Pass!")
            } else {
                println("Failed. Expected: ${testCase.expectedResult}, got: ${result}")
                anyFailed = true
            }
        }

        return !anyFailed
    }

    fun run(input: Input) {
        runA(input)
        runB(input)
    }

    fun run(input: String) {
        run(Input(input))
    }
}
