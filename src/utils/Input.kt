package utils

class Input(val text: String) {
    val lines: List<String>
        get() {
            return text.split("\n")
        }
}