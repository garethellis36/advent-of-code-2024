package org.garethellis.adventofcode.twentyfour

class Puzzle3(input: String) : Puzzle(input) {
    override val part1ExampleSolution: Int = 161
    override val part2ExampleSolution: Int = 48

    override fun part1(): Any {
        return Regex("""mul\(([0-9]+,[0-9]+)\)""")
            .findAll(input())
            .fold(0) { sum, m ->
                val (a, b) = m.groupValues[1].split(",")
                sum + (a.toInt() * b.toInt())
            }
    }

    override fun part2(): Any {
        /**
         * This splits the instructions using the tokens "do()", "don't()", and "mul({n},{n})"
         */
        val matches = Regex("""(do\(\))|(don't\(\))|(mul\(([0-9]+,[0-9]+)\))""").findAll(input())

        /**
         * "At the beginning of the program, mul instructions are enabled."
         */
        var enabled = true

        return matches
            .filter {
                when {
                    it.groupValues[0] == "do()" -> {
                        enabled = true
                        false
                    }

                    it.groupValues[0] == "don't()" -> {
                        enabled = false
                        false
                    }

                    /**
                     * Only include the mul() instruction if `enabled` is true
                     */
                    else -> enabled
                }
            }
            /**
             * We should now be left with a list of matches for the enabled "mul()" tokens, which can be processed similarly to the part 1 solution
             */
            .fold(0) { sum, m ->
                val (a, b) = m.groupValues[4].split(",")
                sum + a.toInt() * b.toInt()
            }
    }
}
