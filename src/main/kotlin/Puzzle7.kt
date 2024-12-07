package org.garethellis.adventofcode.twentyfour

import java.math.BigInteger

typealias Equation = Pair<BigInteger, List<BigInteger>>

interface Calculator {
    fun calculate(i: BigInteger, i2: BigInteger): BigInteger
}

enum class Operator : Calculator {
    Plus, Multiply;

    override fun calculate(i: BigInteger, i2: BigInteger): BigInteger = when (this) {
        Plus -> i + i2
        Multiply -> i * i2
    }
}

enum class ExtendedOperator : Calculator {
    Plus, Multiply, Concatenate;

    override fun calculate(i: BigInteger, i2: BigInteger): BigInteger = when (this) {
        Plus -> i + i2
        Multiply -> i * i2
        Concatenate -> (i.toString() + i2.toString()).toBigInteger()
    }
}

class Puzzle7(input: String) : Puzzle(input) {
    override fun part1(): BigInteger = solveWith(Operator.entries)
    override fun part2(): BigInteger = solveWith(ExtendedOperator.entries)

    private fun solveWith(calculators: List<Calculator>): BigInteger = equations().fold(BigInteger.ZERO) { total, eq ->
        val (expectedAnswer, _) = eq
        if (canBeSolved(eq, calculators)) total + expectedAnswer
        else total
    }

    private fun canBeSolved(eq: Equation, operators: List<Calculator>): Boolean {
        val (expectedResult, inputs) = eq
        val possibleAnswers = mutableListOf<MutableList<BigInteger>>()

        inputs.forEachIndexed { i, value ->
            if (i == 0) {
                possibleAnswers.add(mutableListOf(value))
                return@forEachIndexed
            }

            val answers = mutableListOf<BigInteger>()
            operators.forEach { op ->
                possibleAnswers[i - 1].forEach { answer -> answers.add(op.calculate(answer, value)) }
            }
            possibleAnswers.add(answers)
        }

        return possibleAnswers[inputs.lastIndex].contains(expectedResult)
    }

    private fun equations() = input().split('\n').map { line ->
        val (result, inputsString) = line.split(": ")
        Pair(result.toBigInteger(), inputsString.split(' ').map(String::toBigInteger))
    }

    override val part1ExampleSolution: BigInteger = BigInteger.valueOf(3749)
    override val part2ExampleSolution: BigInteger = BigInteger.valueOf(11387)
}