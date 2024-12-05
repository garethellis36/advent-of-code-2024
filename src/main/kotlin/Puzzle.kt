package org.garethellis.adventofcode.twentyfour

import java.io.File

abstract class Puzzle(private val inputFile: String) {

    abstract fun part1(): Any
    abstract fun part2(): Any
    abstract val part1ExampleSolution: Any
    abstract val part2ExampleSolution: Any

    protected fun inputFile(): String {
        return inputFile
    }

    protected fun input(): String {
        return File(inputFile()).readText().trim('\n')
    }
}
