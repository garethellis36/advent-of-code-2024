package org.garethellis.adventofcode.twentyfour

typealias GridCoordinate = Pair<Int, Int>
typealias GridCell = Char
typealias GridRow = List<GridCell>

class Puzzle4(inputFile: String) : Puzzle(inputFile) {
    override val part1ExampleSolution: Int = 18
    override val part2ExampleSolution: Int = 9

    override fun part1(): Any {
        return compileGrid().findXmas()
    }

    override fun part2(): Any {
        return compileGrid().findXmasCrosses()
    }

    private fun compileGrid(): Grid {
        return Grid(input().split('\n').map { it.toCharArray().toList() })
    }
}

class Grid(private val rows: List<GridRow>) {
    fun findXmas(): Int {
        return findAll('X').fold(0) { total, xPosition ->
            total + ((if (xmasStartsFrom(xPosition, GridDirection.UP_LEFT)) 1 else 0)
                    + (if (xmasStartsFrom(xPosition, GridDirection.UP)) 1 else 0)
                    + (if (xmasStartsFrom(xPosition, GridDirection.UP_RIGHT)) 1 else 0)
                    + (if (xmasStartsFrom(xPosition, GridDirection.LEFT)) 1 else 0)
                    + (if (xmasStartsFrom(xPosition, GridDirection.RIGHT)) 1 else 0)
                    + (if (xmasStartsFrom(xPosition, GridDirection.DOWN_LEFT)) 1 else 0)
                    + (if (xmasStartsFrom(xPosition, GridDirection.DOWN)) 1 else 0)
                    + (if (xmasStartsFrom(xPosition, GridDirection.DOWN_RIGHT)) 1 else 0))
        }
    }

    fun findXmasCrosses(): Int {
        return findAll('A').fold(0) { total, aPosition ->
            /**
             * An 'A' can only have an 'M' or an 'S' in the ordinal directions
             */
            val ordinalNeighbours = mapOf(
                GridDirection.UP_LEFT to letterAt(move(aPosition, GridDirection.UP_LEFT)),
                GridDirection.DOWN_RIGHT to letterAt(move(aPosition, GridDirection.DOWN_RIGHT)),
                GridDirection.DOWN_LEFT to letterAt(move(aPosition, GridDirection.DOWN_LEFT)),
                GridDirection.UP_RIGHT to letterAt(move(aPosition, GridDirection.UP_RIGHT))
            ).filter { (_, letter) -> letter == 'M' || letter == 'S' }

            /**
             * If we don't have 4 ordinal neighbours left, it definitely can't be an X-MAS
             */
            if (ordinalNeighbours.count() < 4) return@fold total

            /**
             * To form an X-MAS, the diagonally opposite neighbours must have different values
             */
            if (ordinalNeighbours[GridDirection.UP_LEFT] == ordinalNeighbours[GridDirection.DOWN_RIGHT]) return@fold total
            if (ordinalNeighbours[GridDirection.DOWN_LEFT] == ordinalNeighbours[GridDirection.UP_RIGHT]) return@fold total

            total + 1
        }
    }

    private fun findAll(letter: Char): Set<GridCoordinate> {
        var coords = setOf<GridCoordinate>()
        rows.forEachIndexed() { r, cells ->
            cells.forEachIndexed() { c, cell ->
                if (cell == letter) {
                    coords += GridCoordinate(r, c)
                }
            }
        }
        return coords
    }

    private fun xmasStartsFrom(x: GridCoordinate, dir: GridDirection): Boolean {
        val mCoordinate = move(x, dir)
        if (letterAt(mCoordinate) != 'M') return false

        val aCoordinate = move(mCoordinate, dir)
        if (letterAt(aCoordinate) != 'A') return false

        val sCoordinate = move(aCoordinate, dir)

        return letterAt(sCoordinate) == 'S'
    }

    private fun letterAt(coord: GridCoordinate): Char? {
        if (!has(coord)) return null
        val (row, col) = coord
        return rows[row][col]
    }

    private fun has(coord: GridCoordinate): Boolean {
        val (row, col) = coord
        return rows.elementAtOrNull(row)?.elementAtOrNull(col) !== null
    }

    private fun move(current: GridCoordinate, direction: GridDirection): GridCoordinate {
        val (currentRow, currentColumn) = current

        return when (direction) {
            GridDirection.UP -> Pair(currentRow - 1, currentColumn)
            GridDirection.DOWN -> Pair(currentRow + 1, currentColumn)
            GridDirection.LEFT -> Pair(currentRow, currentColumn - 1)
            GridDirection.RIGHT -> Pair(currentRow, currentColumn + 1)
            GridDirection.UP_LEFT -> Pair(currentRow - 1, currentColumn - 1)
            GridDirection.UP_RIGHT -> Pair(currentRow - 1, currentColumn + 1)
            GridDirection.DOWN_LEFT -> Pair(currentRow + 1, currentColumn - 1)
            GridDirection.DOWN_RIGHT -> Pair(currentRow + 1, currentColumn + 1)
        }
    }
}

enum class GridDirection {
    UP, DOWN, LEFT, RIGHT, UP_LEFT, UP_RIGHT, DOWN_LEFT, DOWN_RIGHT
}
