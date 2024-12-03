fun main(args: Array<String>) {
    val puzzleNumber = getPuzzleNumber(args)

    when {
        puzzleExists(puzzleNumber) -> {
            runPuzzle(puzzleNumber)
        }

        else -> println("No puzzle class found [$puzzleNumber]")
    }
}

fun puzzleExists(puzzleNumber: Int): Boolean {
    return try {
        Class.forName("Puzzle$puzzleNumber")
        true
    } catch (e: ClassNotFoundException) {
        false
    }
}

fun runPuzzle(puzzleNumber: Int) {
    val puzzle = Class.forName("Puzzle$puzzleNumber").getDeclaredConstructor().newInstance() as Puzzle

    println("\n*** ADVENT OF CODE 2024 ***")
    println("***Day #$puzzleNumber ***\n")
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
