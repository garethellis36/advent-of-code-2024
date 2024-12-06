package org.garethellis.adventofcode.twentyfour

class Puzzle6(inputFile: String) : Puzzle(inputFile) {
    override val part1ExampleSolution: Int = 41
    override val part2ExampleSolution: Int = 0

    override fun part1(): Any {
        var (guard, lab) = initLab()
        val positions = mutableSetOf<LabPosition>()

        while (!guard.hasExitedLab()) {
            positions.add(guard.position)
            guard = guard.patrol(lab)
        }

        return positions.count()
    }

    override fun part2(): Any {
        return -1
    }

    private fun initLab(): Pair<Guard, Labaratory> {
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

        return Pair(Guard(guardPosition!!, GuardOrientation.Up), Labaratory(rows.toList()))
    }
}

class Labaratory(private val rows: List<List<LabPosition>>) {
    fun has(position: LabPosition): Boolean = rows.elementAtOrNull(position.row)?.elementAtOrNull(position.col) != null

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

class Guard(val position: LabPosition, private val orientation: GuardOrientation) {
    fun patrol(lab: Labaratory): Guard {
        val next = lab.moveFrom(position, orientation)
        return if (next.hasObstacle) Guard(position, orientation.rotateRight()) else Guard(next, orientation)
    }

    fun hasExitedLab(): Boolean = position.isInLab()
}
