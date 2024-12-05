package org.garethellis.adventofcode.twentyfour

typealias PageNumber = Int
typealias PageOrderingRule = Pair<PageNumber, PageNumber>

class Puzzle5(inputFile: String) : Puzzle(inputFile) {
    override val part1ExampleSolution: Int = 143
    override val part2ExampleSolution: Int = 123

    override fun part1(): Int = updates()
        .filter(Update::isInCorrectOrder)
        .map(Update::middlePage)
        .sum()

    override fun part2(): Int = updates()
        .filter(Update::isNotInCorrectOrder)
        .map { it.sort().middlePage() }
        .sum()

    private fun updates(): List<Update> {
        val (a, b) = input().split("\n\n")

        val pageOrderingRules = a
            .split('\n')
            .map {
                val (p1, p2) = it.split("|").map(String::toInt)
                Pair(p1, p2)
            }

        return b
            .split('\n')
            .map { it.split(',').map(String::toInt) }
            .map { Update(pageOrderingRules, it) }
    }
}

class Update(private val pageOrderingRules: List<PageOrderingRule>, private val pageNumbers: List<PageNumber>) {
    fun isInCorrectOrder(): Boolean {
        /**
         * We need to see if any of the ordering rules are not followed by the actual sequence of page numbers
         */
        pageOrderingRules.forEach { rule ->
            val (a, b) = rule

            val aPos = pageNumbers.indexOf(a)
            val bPos = pageNumbers.indexOf(b)

            // If either page numbers in this rule do not appear in the list of page numbers, this is fine
            if (aPos == -1 || bPos == -1) return@forEach

            // if the first number in the ordering rule appears in the list after the second, this is not fine
            if (aPos > bPos) return false
        }

        // all page ordering rules followed
        return true
    }

    fun isNotInCorrectOrder(): Boolean = !isInCorrectOrder()

    fun middlePage(): Int = this.pageNumbers[this.pageNumbers.lastIndex / 2]

    fun sort(): Update {
        val sortedPageNumbers = pageNumbers
            .sortedWith { p1, p2 -> p1.compareTo(p2) }

        return Update(pageOrderingRules, sortedPageNumbers)
    }
}
