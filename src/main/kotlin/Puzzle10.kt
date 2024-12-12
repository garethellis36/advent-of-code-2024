package org.garethellis.adventofcode.twentyfour

typealias TrailMapGridCoordinate = String
typealias TrailMapPositions = Map<TrailMapGridCoordinate, Int>
typealias TrailheadPosition = TrailMapGridCoordinate

class Puzzle10(input: String) : Puzzle(input) {
    override val part1ExampleSolution: Int = 36
    override val part2ExampleSolution: Int = 81

    override fun part1(): Int = createMap().trailheads().fold(0) { s, th -> s + th.score() }
    override fun part2(): Int = createMap().trailheads().fold(0) { s, th -> s + th.rating() }

    private fun createMap(): TrailMap {
        val positions: MutableMap<TrailMapGridCoordinate, Int> = mutableMapOf()
        input().split("\n").forEachIndexed { r, chars ->
            chars.toCharArray().forEachIndexed { c, ch -> positions["${r}_${c}"] = ch.digitToInt() }
        }
        return TrailMap(positions.toMap())
    }
}

class TrailMap(private val positions: TrailMapPositions) {
    fun trailheads(): List<Trailhead> = positions.filter { entry -> entry.value == 0 }.keys.map { Trailhead(it, this) }
    fun levelAt(position: TrailheadPosition): Int? = positions[position]
}

class Trailhead(private val position: TrailheadPosition, private val map: TrailMap) {
    /**
     * score = number of unique highest points you can reach from this trailhead
     */
    fun score(): Int = reachableHighestPoints().toSet().count()

    /**
     * rating = number of trails which reach any of the accessible highest points (i.e. some trailheads lead to the same
     * highest point via multiple trails)
     */
    fun rating(): Int = reachableHighestPoints().count()

    private fun reachableHighestPoints(): List<TrailMapGridCoordinate> {
        var positions = listOf(position)

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

        return listOf(up, down, left, right).filter { map.levelAt(it) == currentLevel + 1 }
    }
}


