package utils

class TestCase(val input: Input, val expectedResult: Any) {
    constructor(input: String, expectedResult: Any) : this(Input(input), expectedResult)
    constructor(input: List<String>, expectedResult: Any) : this(Input(input.joinToString("\n")), expectedResult)
}
