package org.garethellis.adventofcode.twentyfour

import java.math.BigInteger

enum class Operator {
    Plus, Multiply;

    fun calculate(i: BigInteger, i2: BigInteger): BigInteger = when (this) {
        Plus -> i + i2
        Multiply -> i * i2
    }
}

class Puzzle7(input: String) : Puzzle(input) {
    override fun part1(): BigInteger {
        return equations()
            .fold(BigInteger.ZERO) { total, eq ->
                val (expectedResult, inputs) = eq

                val possibleAnswers = mutableListOf<MutableList<BigInteger>>()
                val operators = listOf(Operator.Plus, Operator.Multiply)
                inputs.forEachIndexed { i, v ->
                    if (i == 0) {
                        possibleAnswers.add(mutableListOf(v))
                        return@forEachIndexed
                    }

                    val out = mutableListOf<BigInteger>()
                    operators.forEach { op ->
                        possibleAnswers[i - 1].forEach {
                            out.add(op.calculate(v, it))
                        }
                    }
                    possibleAnswers.add(out)
                }

                if (possibleAnswers[inputs.lastIndex].contains(expectedResult)) total + expectedResult
                else total
            }
    }

    private fun equations() = input().split('\n').map { line ->
        val (result, inputsString) = line.split(": ")
        Pair(result.toBigInteger(), inputsString.split(' ').map(String::toBigInteger))
    }

    override fun part2(): BigInteger {
        return BigInteger.valueOf(-1)
    }

    override val part1ExampleSolution: BigInteger = BigInteger.valueOf(3749)
    override val part2ExampleSolution: BigInteger = BigInteger.ZERO
}