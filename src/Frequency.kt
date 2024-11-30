class Frequency<T>(elements: List<T> = listOf()) {
    val map = HashMap<T, Int>()

    init {
        insertAll(elements)
    }

    fun increment(value: T) {
        this[value] += 1
    }

    fun decrement(value: T) {
        this[value] -= 1
    }

    fun insertAll(values: List<T>) {
        for (value in values) {
            increment(value)
        }
    }

    fun mostFrequent(): List<Pair<T, Int>> {
        return map.toList().sortedByDescending { entry -> entry.second }
    }

    operator fun get(key: T): Int {
        return map[key] ?: 0
    }

    operator fun set(key: T, value: Int) {
        if (value == 0) {
            map.remove(key)
        } else {
            map[key] = value
        }
    }
}