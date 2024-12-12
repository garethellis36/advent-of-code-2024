package org.garethellis.adventofcode.twentyfour

typealias TrailMapGridCoordinate = String
typealias TrailMapPositions = MutableMap<TrailMapGridCoordinate, Int>
typealias TrailheadPosition = TrailMapGridCoordinate
typealias TrailheadPositions = Set<TrailheadPosition>

class Puzzle10(input: String) : Puzzle(input) {
    override val part1ExampleSolution: Int = 36
    override val part2ExampleSolution: Int = 81

    override fun part1(): Int {
        val map = createMap()
        return map.trailheads().fold(0) { s, th -> s + map.scoreOf(th) }
    }

    override fun part2(): Int {
        val map = createMap()
        return map.trailheads().fold(0) { s, th -> s + map.ratingOf(th) }
    }

    private fun createMap(): TrailMap {
        val positions: TrailMapPositions = mutableMapOf()
        input().split("\n").forEachIndexed { r, chars ->
            chars.toCharArray().forEachIndexed { c, ch -> positions["${r}_${c}"] = ch.digitToInt() }
        }
        return TrailMap(positions)
    }
}

class TrailMap(private val positions: TrailMapPositions) {
    fun trailheads(): TrailheadPositions = positions.filter { entry -> entry.value == 0 }.keys

    /**
     * score = number of unique highest points you can reach from this trailhead
     */
    fun scoreOf(trailhead: TrailheadPosition): Int = reachableHighestPointsFrom(trailhead).toSet().count()

    /**
     * rating = number of trails which reach any of the accessible highest points (i.e. some trailheads lead to the same
     * highest point via multiple trails)
     */
    fun ratingOf(trailhead: TrailheadPosition): Int = reachableHighestPointsFrom(trailhead).count()

    private fun reachableHighestPointsFrom(trailhead: TrailheadPosition): List<TrailMapGridCoordinate> {
        var positions = listOf(trailhead)

        (0..<9).forEach { l ->
            val newPositions = mutableListOf<TrailheadPosition>()
            positions.forEach { p -> nextLevels(p, l).forEach { np -> newPositions.add(np) } }
            positions = newPositions.toList()
        }

        return positions
    }

    private fun nextLevels(
        currentPosition: TrailMapGridCoordinate,
        currentLevel: Int
    ): Iterable<TrailMapGridCoordinate> {
        val (row, col) = currentPosition.split("_").map(String::toInt)

        val up = "${row - 1}_${col}"
        val down = "${row + 1}_${col}"
        val left = "${row}_${col - 1}"
        val right = "${row}_${col + 1}"

        return listOf(up, down, left, right)
            .filter { positions.contains(it) && positions[it] == currentLevel + 1 }
    }
}
