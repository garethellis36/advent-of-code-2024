package org.garethellis.adventofcode.twentyfour

abstract class Puzzle(private val input: String) {

    abstract fun part1(): Any
    abstract fun part2(): Any
    abstract val part1ExampleSolution: Any
    abstract val part2ExampleSolution: Any

    protected fun input(): String {
        return input
    }
}
