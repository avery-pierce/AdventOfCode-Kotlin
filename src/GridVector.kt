data class GridVector(val dx: Int, val dy: Int) {
    companion object {
        val north: GridVector = GridVector(0, 1)
        val south: GridVector = GridVector(0, -1)
        val west: GridVector = GridVector(-1, 0)
        val east: GridVector = GridVector(1, 0)
    }

    operator fun plus(otherVector: GridVector): GridVector {
        return GridVector(dx + otherVector.dx, dy + otherVector.dy)
    }

    operator fun times(multiplier: Int): GridVector {
        return GridVector(dx * multiplier, dy * multiplier)
    }

    fun rotatedClockwise(): GridVector {
        val dx2 = dy
        val dy2 = -dx
        return GridVector(dx2, dy2)
    }

    fun rotatedCounterClockwise(): GridVector {
        val dx2 = -dy
        val dy2 = dx
        return GridVector(dx2, dy2)
    }
}
