import java.io.File

interface Puzzle {
    fun puzzleNumber(): Int
    fun part1(): Any
    fun part2(): Any

    fun useSampleInput(): Boolean {
        return false
    }

    fun inputFile(): String {
        var inputFile = "puzzle${puzzleNumber()}"
        if (useSampleInput()) {
            inputFile += "_sample"
        }

        return inputFile
    }

    fun input(): String {
        return File("./input/${inputFile()}").readText().trim('\n')
    }
}
