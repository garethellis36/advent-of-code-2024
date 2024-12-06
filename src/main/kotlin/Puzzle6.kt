package org.garethellis.adventofcode.twentyfour

class Puzzle6(inputFile: String) : Puzzle(inputFile) {
    override val part1ExampleSolution: Int = 41
    override val part2ExampleSolution: Int = 6

    override fun part1(): Any {
        return positionsOfSafePath().count()
    }

    override fun part2(): Any {
        val (guard, ogLab) = initLab()

        val possibleLabs = positionsOfSafePath().mapIndexed { i, pos -> if (i == 0) ogLab else ogLab.addObstacle(pos) }

        return possibleLabs.fold(0) { total, labWithExtraObstacle ->
            var g = guard
            val positions = mutableSetOf<Pair<LabPosition, GuardOrientation>>()

            while (!g.hasExitedLab()) {
                // guard has already visited this position and in this orientation, and is looping
                if (positions.contains(Pair(g.position, g.orientation))) {
                    return@fold total + 1
                }

                positions.add(Pair(g.position, g.orientation))
                g = g.patrol(labWithExtraObstacle)
            }

            // guard is still able to exit the lab, adding an obstacle here did not make her loop
            total
        }
    }

    private fun positionsOfSafePath(): Set<LabPosition> {
        var (guard, lab) = initLab()
        val positions = mutableSetOf<LabPosition>()

        while (!guard.hasExitedLab()) {
            positions.add(guard.position)
            guard = guard.patrol(lab)
        }

        return positions
    }

    private fun initLab(): Pair<Guard, Laboratory> {
        val lines = input().split('\n')

        var guardPosition: LabPosition? = null
        val rows = mutableListOf<List<LabPosition>>()

        lines.forEachIndexed { lineIndex, line ->
            val columns = mutableListOf<LabPosition>()
            line.toCharArray().toList().forEachIndexed { charIndex, char ->
                val pos = LabPosition(lineIndex, charIndex, char == '#')
                if (char == '^') {
                    guardPosition = pos
                }
                columns.add(pos)
            }
            rows.add(columns.toList())
        }

        return Pair(Guard(guardPosition!!, GuardOrientation.Up), Laboratory(rows.toList()))
    }
}

class Laboratory(private val rows: List<List<LabPosition>>) {
    fun has(position: LabPosition): Boolean = rows.elementAtOrNull(position.row)?.elementAtOrNull(position.col) != null

    fun addObstacle(position: LabPosition): Laboratory {
        return Laboratory(rows.mapIndexed rowMapper@{ rowIndex, cols ->
            if (rowIndex != position.row) return@rowMapper cols
            cols.mapIndexed colMapper@{ colIndex, pos ->
                if (colIndex != position.col) return@colMapper pos
                LabPosition(pos.row, pos.col, true)
            }
        })
    }

    fun moveFrom(position: LabPosition, direction: GuardOrientation): LabPosition {
        val (nextRow, nextCol) = when (direction) {
            GuardOrientation.Up -> Pair(position.row - 1, position.col)
            GuardOrientation.Right -> Pair(position.row, position.col + 1)
            GuardOrientation.Down -> Pair(position.row + 1, position.col)
            GuardOrientation.Left -> Pair(position.row, position.col - 1)
        }

        if (rows.elementAtOrNull(nextRow) == null) return LabPosition(-1, -1, false)
        if (rows[nextRow].elementAtOrNull(nextCol) == null) return LabPosition(-1, -1, false)

        return rows[nextRow][nextCol]
    }
}


enum class GuardOrientation {
    Up, Down, Left, Right;

    fun rotateRight(): GuardOrientation {
        return when (this) {
            Up -> Right
            Right -> Down
            Down -> Left
            Left -> Up
        }
    }
}

class LabPosition(val row: Int, val col: Int, val hasObstacle: Boolean) {
    fun isInLab(): Boolean = row == -1 && col == -1
}

class Guard(val position: LabPosition, val orientation: GuardOrientation) {
    fun patrol(lab: Laboratory): Guard {
        val next = lab.moveFrom(position, orientation)
        return if (next.hasObstacle) Guard(position, orientation.rotateRight()) else Guard(next, orientation)
    }

    fun hasExitedLab(): Boolean = position.isInLab()
}
