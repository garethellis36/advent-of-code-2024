package org.garethellis.adventofcode.twentyfour

import java.io.File

abstract class Puzzle(private val useSampleInput: Boolean) {

    abstract fun puzzleNumber(): Int
    abstract fun part1(): Any
    abstract fun part2(): Any

    protected fun inputFile(): String {
        var inputFile = "puzzle${puzzleNumber()}"
        if (useSampleInput) {
            inputFile += "_sample"
        }

        return inputFile
    }

    protected fun input(): String {
        return File("./input/${inputFile()}").readText().trim('\n')
    }
}
