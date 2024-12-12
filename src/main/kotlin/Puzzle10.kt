package org.garethellis.adventofcode.twentyfour

typealias TrailMapGridCoordinate = String
typealias TrailMapPositions = MutableMap<TrailMapGridCoordinate, Int>
typealias TrailheadPosition = TrailMapGridCoordinate
typealias TrailheadPositions = Set<TrailheadPosition>

class TrailMap(private val positions: TrailMapPositions) {
    fun trailheads(): TrailheadPositions = positions.filter { entry -> entry.value == 0 }.keys

    fun scoreOf(trailhead: TrailheadPosition): Int {
        var positions = setOf(trailhead)

        (0..<9).forEach { l ->
            val newPositions = mutableSetOf<TrailheadPosition>()
            positions.forEach { p -> nextLevels(p, l).forEach { np -> newPositions.add(np) } }
            positions = newPositions.toSet()
        }

        return positions.count()
    }

    private fun nextLevels(currentPosition: TrailMapGridCoordinate, currentLevel: Int): Set<TrailMapGridCoordinate> {
        val (row, col) = currentPosition.split("_").map(String::toInt)

        val up = "${row - 1}_${col}"
        val down = "${row + 1}_${col}"
        val left = "${row}_${col - 1}"
        val right = "${row}_${col + 1}"

        return listOf(up, down, left, right)
            .filter { positions.contains(it) && positions[it] == currentLevel + 1 }
            .toSet()
    }

    private fun valueAt(row: Int, col: Int) = positions["${row}_${col}"]
}

class Puzzle10(input: String) : Puzzle(input) {
    override val part1ExampleSolution: Int = 36
    override val part2ExampleSolution: Int = -1

    override fun part1(): Int {
        val map = createMap()
        return map.trailheads().fold(0) { s, th -> s + map.scoreOf(th) }
    }

    override fun part2(): Int {
        return 0
    }

    private fun createMap(): TrailMap {
        val positions: TrailMapPositions = mutableMapOf()
        input().split("\n").forEachIndexed { r, chars ->
            chars.toCharArray().forEachIndexed { c, ch -> positions["${r}_${c}"] = ch.digitToInt() }
        }
        return TrailMap(positions)
    }
}
