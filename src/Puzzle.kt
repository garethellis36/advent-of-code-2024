import java.io.File

interface Puzzle {
    fun puzzleNumber(): Int
    fun part1(): Any
    fun part2(): Any

    fun useSampleInput(): Boolean {
        return false
    }

    fun input(): String {
        var inputFile = "puzzle${puzzleNumber()}"
        if (useSampleInput()) {
            inputFile += "_sample"
        }

        return File("./input/${inputFile}").readText()
    }
}
