package org.garethellis.adventofcode.twentyfour

class Puzzle11(input: String) : Puzzle(input) {
    val numberOfBlinks = 25

    override val part1ExampleSolution: Long = 55312
    override val part2ExampleSolution: Long = -1

    override fun part1(): Long {
        var stones = Stones(input().split(" ").map(String::toLong))

        for (blink in 1..numberOfBlinks) {
            stones = stones.refresh()
        }

        return stones.count()
    }

    override fun part2(): Long {
        return 0
    }
}

class Stones(private val stones: List<Long>) {
    fun refresh(): Stones = Stones(
        stones.map {
            if (it == 0L) return@map listOf(1L)

            val asString: String = it.toString()
            if (asString.length % 2 == 0) return@map asString.chunked(asString.length / 2).map(String::toLong)

            listOf(it * 2024)
        }.flatten()
    )

    fun count(): Long {
        return stones.count().toLong()
    }

    private fun toList() = stones

    fun print() {
        println("${count()}: ${toList()}")
    }
}
