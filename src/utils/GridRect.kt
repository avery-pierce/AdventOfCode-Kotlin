package utils

data class GridRect(var origin: GridPoint, var size: GridSize) {
    var width: Int
        get() { return this.size.width }
        set(value) { this.size.width = value }

    var height: Int
        get() { return this.size.height }
        set(value) { this.size.height = value }

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

}

data class GridSize(var width: Int, var height: Int)