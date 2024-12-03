class Puzzle3 : Puzzle {
    override fun puzzleNumber(): Int {
        return 3
    }

    override fun part1(): Any {
        val instructions = input()

        return Regex("""mul\(([0-9]+,[0-9]+)\)""")
            .findAll(instructions)
            .map { instruction ->
                val (a, b) = instruction.groupValues[1].split(",")
                a.toInt() * b.toInt()
            }
            .sum()
    }

    override fun part2(): Any {
        return 0
    }
}
