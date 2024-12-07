package org.garethellis.adventofcode.twentyfour

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.types.int
import com.github.ajalt.mordant.rendering.TextColors.brightGreen
import com.github.ajalt.mordant.rendering.TextColors.brightRed
import java.io.File

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

        val part1ExampleInputFile = "./input/puzzle${puzzleNumber}_example"
        val realInputFile = "./input/puzzle$puzzleNumber"
        val part2ExampleInputFile = if (puzzleNumber == 3) "./input/puzzle3_part2_example" else part1ExampleInputFile

        val createPuzzle = { inputFile: String ->
            val input = File(inputFile).readText().trim('\n')

            Class.forName("org.garethellis.adventofcode.twentyfour.Puzzle$puzzleNumber")
                .getDeclaredConstructor(String::class.java)
                .newInstance(input) as Puzzle
        }

        echo("\n*** ADVENT OF CODE 2024 ***")
        echo("*** Day #$puzzleNumber ***")
        echo()

        val puzzleExamplePart1 = createPuzzle(part1ExampleInputFile)
        val puzzleExamplePart2 = createPuzzle(part2ExampleInputFile)

        echo("Using example input:")
        echo(exampleResultOutput(1, puzzleExamplePart1.part1ExampleSolution, puzzleExamplePart1.part1()))
        echo(exampleResultOutput(2, puzzleExamplePart1.part2ExampleSolution, puzzleExamplePart2.part2()))
        echo()

        val puzzle = createPuzzle(realInputFile)
        echo("Using real input:")
        echo(realResultOutput(1, puzzle.part1()))
        echo(realResultOutput(2, puzzle.part2()))
    }

    private fun exampleResultOutput(part: Int, expected: Any, actual: Any): String {
        return "Part $part solution: $actual " + (if (expected == actual) brightGreen("OK") else brightRed("expected $expected"))
    }

    private fun realResultOutput(part: Int, result: Any): String {
        return "Part $part solution: $result"
    }
}
