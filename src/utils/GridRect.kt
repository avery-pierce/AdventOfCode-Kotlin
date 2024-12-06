package utils

import kotlin.math.max
import kotlin.math.min

data class GridRect(var origin: GridPoint, var size: GridSize) {
    companion object {
        fun enclosing(coordinates: List<GridPoint>): GridRect {
            return enclosing(HashSet(coordinates))
        }

        fun enclosing(coordinates: Set<GridPoint>): GridRect {
            if (coordinates.isEmpty()) return GridRect(GridPoint.zero, GridSize(0, 0))

            var minX = coordinates.first().x
            var minY = coordinates.first().y
            var maxX = minX
            var maxY = minY

            for (coord in coordinates) {
                minX = min(coord.x, minX)
                minY = min(coord.y, minY)
                maxX = max(coord.x, maxX)
                maxY = max(coord.y, maxY)
            }

            val origin = GridPoint(minX, minY)
            val width = maxX - minX
            val height = maxY - minY
            val size = GridSize(width, height)

            return GridRect(origin, size)
        }
    }
    var width: Int
        get() { return this.size.width }
        set(value) { this.size.width = value }

    var height: Int
        get() { return this.size.height }
        set(value) { this.size.height = value }

    val maxX: Int
        get() { return origin.x + width }

    val maxY: Int
        get() { return origin.y + height }

    val coordinates: Set<GridPoint>
        get() {
            val points = HashSet<GridPoint>()

            for (x in (origin.x ..< origin.x + size.width)) {
                for (y in (origin.y ..< origin.y + size.height)) {
                    points.add(GridPoint(x, y))
                }
            }

            return points
        }

    fun contains(point: GridPoint): Boolean {
        val xInBounds = origin.x <= point.x && point.x <= maxX
        val yInBounds = origin.y <= point.y && point.y <= maxY
        return xInBounds && yInBounds
    }
}

data class GridSize(var width: Int, var height: Int)