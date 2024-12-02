import java.io.File
import kotlin.math.absoluteValue

typealias Level = Int
typealias Report = List<Level>

class Puzzle2 : Puzzle {
    override fun part1(): Int {
        return reports()
            .map { report -> isSafe(report) }
            .filter { isSafe -> isSafe }
            .count()
    }

    override fun part2(): Int {
        return 0
    }

    private fun isSafe(report: Report): Boolean {
        val increasing = report[0] < report[1]

        for (i in 0..<report.lastIndex) {
            if (increasing && report[i] >= report[i + 1]) {
                return false
            }

            if (!increasing && report[i + 1] >= report[i]) {
                return false
            }

            val diff = report[i] - report[i + 1]
            if (diff.absoluteValue < 1 || diff.absoluteValue > 3) {
                return false
            }
        }

        return true
    }

    private fun reports(): List<Report> {
        val input = File("./input/puzzle2").readText()
        val lines = input.split("\n").filter { it.isNotBlank() }

        return lines.map { report ->
            report.split(" ").map { level -> level.toInt() }
        }
    }
}
