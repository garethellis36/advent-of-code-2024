package org.garethellis.adventofcode.twentyfour

typealias Equation = Pair<Long, List<Long>>

enum class Operator {
    Plus, Multiply, Concatenate;

    fun calculate(a: Long, b: Long): Long = when (this) {
        Plus -> a + b
        Multiply -> a * b
        Concatenate -> (a.toString() + b.toString()).toLong()
    }
}

class Puzzle7(input: String) : Puzzle(input) {
    override fun part1(): Long = solveWith(listOf(Operator.Plus, Operator.Multiply))
    override fun part2(): Long = solveWith(Operator.entries)

    private fun solveWith(calculators: List<Operator>): Long = equations().fold(0) { total, eq ->
        val (expectedAnswer, _) = eq
        if (canBeSolved(eq, calculators)) total + expectedAnswer
        else total
    }

    private fun canBeSolved(eq: Equation, operators: List<Operator>): Boolean {
        val (expectedResult, inputs) = eq

        val summed = inputs.sum()
        // too high when summed? impossible to solve using any combination of operators
        if (summed > expectedResult) return false
        // have we happened to solve it just by summing?
        if (summed == expectedResult) return true

        // try every combination of operators...
        return inputs
            .foldIndexed(setOf<Long>()) { i, carry, value ->
                if (i == 0) return@foldIndexed setOf(value)

                val runningTotals = mutableSetOf<Long>()
                operators.forEach { operator ->
                    carry.forEach { answer ->
                        val result = operator.calculate(answer, value)
                        if (result <= expectedResult) runningTotals.add(result)
                    }
                }
                runningTotals.toSet()
            }
            .contains(expectedResult)
    }

    private fun equations() = input().split('\n').map { line ->
        val (result, inputsString) = line.split(": ")
        Pair(result.toLong(), inputsString.split(' ').map(String::toLong))
    }

    override val part1ExampleSolution: Long = 3749
    override val part2ExampleSolution: Long = 11387
}
