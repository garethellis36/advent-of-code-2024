package org.garethellis.adventofcode.twentyfour

import com.github.ajalt.clikt.core.*
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.types.int

class Cli : CliktCommand() {
    val puzzleNumber: Int by argument(help = "The puzzle (day) number of the puzzle").int()

    override fun run() {
        when {
            puzzleExists(puzzleNumber) -> {
                runPuzzle(puzzleNumber)
            }

            else -> echo("No puzzle class found [$puzzleNumber]")
        }
    }

    private fun puzzleExists(puzzleNumber: Int): Boolean {
        return try {
            Class.forName("org.garethellis.adventofcode.twentyfour.Puzzle$puzzleNumber")
            true
        } catch (e: ClassNotFoundException) {
            false
        }
    }

    private fun runPuzzle(puzzleNumber: Int) {
        val puzzle = Class.forName("org.garethellis.adventofcode.twentyfour.Puzzle$puzzleNumber")
            .getDeclaredConstructor()
            .newInstance() as Puzzle

        echo("\n*** ADVENT OF CODE 2024 ***")
        echo("*** Day #$puzzleNumber ***\n")
        echo("Part 1 solution: ${puzzle.part1()}")
        echo("Part 2 solution: ${puzzle.part2()}")
    }
}
