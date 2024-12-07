package org.garethellis.adventofcode.twentyfour

typealias Equation = Pair<Long, List<Long>>

interface Calculator {
    fun calculate(i: Long, i2: Long): Long
}

enum class Operator : Calculator {
    Plus, Multiply;

    override fun calculate(i: Long, i2: Long): Long = when (this) {
        Plus -> i + i2
        Multiply -> i * i2
    }
}

enum class ExtendedOperator : Calculator {
    Plus, Multiply, Concatenate;

    override fun calculate(i: Long, i2: Long): Long = when (this) {
        Plus -> i + i2
        Multiply -> i * i2
        Concatenate -> (i.toString() + i2.toString()).toLong()
    }
}

class Puzzle7(input: String) : Puzzle(input) {
    override fun part1(): Long = solveWith(Operator.entries)
    override fun part2(): Long = solveWith(ExtendedOperator.entries)

    private fun solveWith(calculators: List<Calculator>): Long = equations().fold(0) { total, eq ->
        val (expectedAnswer, _) = eq
        if (canBeSolved(eq, calculators)) total + expectedAnswer
        else total
    }

    private fun canBeSolved(eq: Equation, operators: List<Calculator>): Boolean {
        val (expectedResult, inputs) = eq
        val possibleAnswers = mutableListOf<MutableList<Long>>()

        inputs.forEachIndexed { i, value ->
            if (i == 0) {
                possibleAnswers.add(mutableListOf(value))
                return@forEachIndexed
            }

            val answers = mutableListOf<Long>()
            operators.forEach { op ->
                possibleAnswers[i - 1].forEach { answer ->
                    val result = op.calculate(answer, value)
                    if (result <= expectedResult) answers.add(result)
                }
            }
            possibleAnswers.add(answers)
        }

        return possibleAnswers[inputs.lastIndex].contains(expectedResult)
    }

    private fun equations() = input().split('\n').map { line ->
        val (result, inputsString) = line.split(": ")
        Pair(result.toLong(), inputsString.split(' ').map(String::toLong))
    }

    override val part1ExampleSolution: Long = 3749
    override val part2ExampleSolution: Long = 11387
}