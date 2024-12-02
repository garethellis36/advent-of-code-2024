fun main(args: Array<String>) {
    val puzzleNumber = getPuzzleNumber(args)

    val puzzleFactories = mapOf(
        1 to { Puzzle1() },
        2 to { Puzzle2() },
    )

    val puzzleFactory = puzzleFactories[puzzleNumber]
    if (puzzleFactory == null) {
        println("No puzzle class found [$puzzleNumber]")
        return
    }

    val puzzle = puzzleFactory()

    println("Running puzzle #$puzzleNumber")
    println("Part 1 solution: ${puzzle.part1()}")
    println("Part 2 solution: ${puzzle.part2()}")
}

fun getPuzzleNumber(args: Array<String>): Int {
    val puzzleNumber = args.firstOrNull()
    if (puzzleNumber != null) {
        return puzzleNumber.toInt()
    }

    return getPuzzleNumberFromStdIn()
}

fun getPuzzleNumberFromStdIn(): Int {
    println("Enter the puzzle number to run:")
    val puzzleNumber = readln().toIntOrNull()
    if (puzzleNumber != null) {
        return puzzleNumber
    }
    return getPuzzleNumberFromStdIn()
}
