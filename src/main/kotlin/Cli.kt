package org.garethellis.adventofcode.twentyfour

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.types.int
import com.github.ajalt.mordant.rendering.TextColors.brightGreen
import com.github.ajalt.mordant.rendering.TextColors.brightRed
import java.io.File
import kotlin.time.Duration
import kotlin.time.measureTimedValue

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

        echo("Using example input:")
        runAndReportExample(1, createPuzzle(part1ExampleInputFile))
        runAndReportExample(2, createPuzzle(part2ExampleInputFile))
        echo()

        val puzzle = createPuzzle(realInputFile)
        echo("Using real input:")
        runAndReportReal(1, puzzle)
        runAndReportReal(2, puzzle)
    }

    private fun runAndReportExample(part: Int, puzzle: Puzzle) {
        val (result, duration) = measureTimedValue { if (part == 1) puzzle.part1() else puzzle.part2() }
        val expected = if (part == 1) puzzle.part1ExampleSolution else puzzle.part2ExampleSolution
        echo("Part $part solution: $result " + (if (expected == result) brightGreen("OK") else brightRed("expected $expected")) + " [took ${duration}]")
    }

    private fun runAndReportReal(part: Int, puzzle: Puzzle) {
        val (result, duration) = measureTimedValue { if (part == 1) puzzle.part1() else puzzle.part2() }
        echo("Part $part solution: $result [took ${duration}]")
    }

    private fun getFriendlyDuration(duration: Duration): String {
        return duration.toString()
    }
}
