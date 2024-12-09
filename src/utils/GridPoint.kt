package utils

import kotlin.math.abs

data class GridPoint(var x: Int, var y: Int) {
    companion object {
        val zero = GridPoint(0, 0)
    }

    operator fun plus(vector: GridVector): GridPoint {
        return GridPoint(x + vector.dx, y + vector.dy)
    }

    fun vectorTo(point: GridPoint): GridVector {
        val dx = point.x - x
        val dy = point.y - y
        return GridVector(dx, dy)
    }

    fun manhattanDistanceTo(point: GridPoint): Int {
        val dx = abs(x - point.x)
        val dy = abs(y - point.y)
        return dx + dy
    }
}