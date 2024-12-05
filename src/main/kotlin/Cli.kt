package org.garethellis.adventofcode.twentyfour

import com.github.ajalt.clikt.core.CliktCommand
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

        val part1SampleInputFile = "./input/puzzle${puzzleNumber}_sample"
        val realInputFile = "./input/puzzle$puzzleNumber"
        val part2SampleInputFile = if (puzzleNumber == 3) "./input/puzzle3_part2_sample" else part1SampleInputFile

        val createPuzzle = { inputFile: String ->
            Class.forName("org.garethellis.adventofcode.twentyfour.Puzzle$puzzleNumber")
                .getDeclaredConstructor(String::class.java)
                .newInstance(inputFile) as Puzzle
        }

        echo("\n*** ADVENT OF CODE 2024 ***")
        echo("*** Day #$puzzleNumber ***")
        echo()

        echo("Using sample input:")
        echo("Part 1 solution: ${createPuzzle(part1SampleInputFile).part1()}")
        echo("Part 2 solution: ${createPuzzle(part2SampleInputFile).part2()}")
        echo()

        echo("Using real input:")
        echo("Part 1 solution: ${createPuzzle(realInputFile).part1()}")
        echo("Part 2 solution: ${createPuzzle(realInputFile).part2()}")
    }
}
