package org.garethellis.adventofcode.twentyfour

typealias PageNumber = Int
typealias PageOrderingRules = Map<Int, Set<Int>>

class Puzzle5(input: String) : Puzzle(input) {
    override val part1ExampleSolution: Int = 143
    override val part2ExampleSolution: Int = 123

    override fun part1(): Int = updates()
        .filter(Update::isInCorrectOrder)
        .fold(0) { total, update -> total + update.middlePage() }

    override fun part2(): Int = updates()
        .filter(Update::isNotInCorrectOrder)
        .fold(0) { total, update -> total + update.sort().middlePage() }

    private fun updates(): List<Update> {
        val (a, b) = input().split("\n\n")

        val pageOrderingRules = mutableMapOf<Int, Set<Int>>()
        a.split('\n').forEach { rule ->
            val (p1, p2) = rule.split("|").map(String::toInt)
            pageOrderingRules[p1] = if (pageOrderingRules.containsKey(p1)) pageOrderingRules[p1]!! + p2 else setOf(p2)
        }

        return b.split('\n').map { it.split(',').map(String::toInt) }.map { Update(pageOrderingRules, it) }
    }
}

class Update(private val pageOrderingRules: PageOrderingRules, private val pageNumbers: List<PageNumber>) {
    fun sort(): Update = Update(
        pageOrderingRules,
        pageNumbers.sortedWith { p1, p2 -> if (pageOrderingRules[p1]?.contains(p2) == true) 1 else -1 }
    )

    fun isInCorrectOrder(): Boolean {
        pageOrderingRules.forEach { (a, numbersThatMustFollowIt) ->
            val aPosition = pageNumbers.indexOf(a)
            if (aPosition == -1) return@forEach

            numbersThatMustFollowIt.forEach inner@{ b ->
                val bPosition = pageNumbers.indexOf(b)
                if (bPosition == -1) return@inner
                if (aPosition > bPosition) return false
            }
        }

        return true
    }

    fun isNotInCorrectOrder(): Boolean = !isInCorrectOrder()

    fun middlePage(): Int = this.pageNumbers[this.pageNumbers.lastIndex / 2]
}
