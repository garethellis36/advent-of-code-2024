import java.io.File

abstract class Puzzle() {
    abstract fun puzzleNumber(): Int
    abstract fun part1(): Any
    abstract fun part2(): Any

    fun input(): String {
        val inputFile = "puzzle${puzzleNumber()}"

        return File("./input/${inputFile}").readText()
    }
}
