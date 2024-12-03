class Puzzle3 : Puzzle {
    override fun puzzleNumber(): Int {
        return 3
    }

    override fun part1(): Any {
        return Regex("""mul\(([0-9]+,[0-9]+)\)""")
            .findAll(input())
            .map { m ->
                val (a, b) = m.groupValues[1].split(",")
                a.toInt() * b.toInt()
            }
            .sum()
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
            /**
             * Sequence#filter() is evaluated lazily, so we either have to convert it to a List now or at the end
             * @see [https://kotlinlang.org/docs/sequences.html#sequence]
             */
            .toList()
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
             * We should now be left with a list of matches for the "mul()" tokens, which can be processed similarly to the part 1 solution
             */
            .map {
                val (a, b) = it.groupValues[4].split(",")
                a.toInt() * b.toInt()
            }
            .sum()
    }
}
