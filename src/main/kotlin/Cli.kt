package org.garethellis.adventofcode.twentyfour

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.int

class Cli : CliktCommand() {
    val puzzleNumber: Int by argument(help = "The puzzle (day) number of the puzzle").int()

    val useSample: Boolean by option(help = "Use the sample puzzle input instead of the real input").flag(default = false)

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

        var inputFile = "./input/puzzle$puzzleNumber"
        if (useSample) {
            inputFile += "_sample"
        }

        val createPuzzle = {
            Class.forName("org.garethellis.adventofcode.twentyfour.Puzzle$puzzleNumber")
                .getDeclaredConstructor(String::class.java)
                .newInstance(inputFile) as Puzzle
        }

        echo("\n*** ADVENT OF CODE 2024 ***")
        echo("*** Day #$puzzleNumber ***\n")

        echo("Part 1 solution: ${createPuzzle().part1()}")

        // day 3 has different sample input for part 2
        if (puzzleNumber == 3 && useSample) {
            inputFile = "./input/puzzle3_part2_sample"
        }

        echo("Part 2 solution: ${createPuzzle().part2()}")
    }
}
