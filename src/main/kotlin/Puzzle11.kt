package org.garethellis.adventofcode.twentyfour

class Puzzle11(input: String) : Puzzle(input) {
    val numberOfBlinks = 25

    override val part1ExampleSolution: Long = 55312
    override val part2ExampleSolution: Long = 65601038650482

    override fun part1(): Long = createStones().refresh(25).count()

    override fun part2(): Long = createStones().refresh(75).count()

    private fun createStones(): Stones {
        val numbers = input().split(" ").map(String::toLong)
        val map = mutableMapOf<Long, Long>()
        numbers.forEach { map[it] = (map[it] ?: 0) + 1 }
        return Stones(map.toMap())
    }
}

typealias StoneNumber = Long
typealias Occurrences = Long

class Stones(val stones: Map<StoneNumber, Occurrences>) {
    fun refresh(times: Int): Stones {
        var stonesCopy = stones.toMutableMap()

        repeat(times) {
            val newMap = mutableMapOf<StoneNumber, Occurrences>()
            stonesCopy.forEach { (n, freq) -> nextValues(n).forEach { newMap[it] = (newMap[it] ?: 0) + freq } }
            stonesCopy = newMap
        }

        return Stones(stonesCopy)
    }

    private fun nextValues(n: StoneNumber): List<StoneNumber> {
        if (n == 0L) return listOf(1L)

        if (n.digits().isEven) return n.toString().run {
            chunked(length / 2).map(String::toLong)
        }

        return listOf(n * 2024)
    }

    fun count(): Long = stones.values.sum()

    private fun toList() = stones

    fun print() {
        println("${count()}: ${toList()}")
    }
}
