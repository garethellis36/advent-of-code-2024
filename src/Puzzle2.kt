import kotlin.math.absoluteValue

typealias Level = Int
typealias Report = List<Level>

class Puzzle2 : Puzzle {
    override fun puzzleNumber(): Int {
        return 2
    }

    override fun part1(): Int {
        return reports().filter { isSafe(it) }.count()
    }

    override fun part2(): Int {
        val (safe, unsafe) = reports().partition { isSafe(it) }

        val safeWhenDampened = unsafe.filter { isSafeWhenDampened(it) }

        return safe.count() + safeWhenDampened.count()
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

    private fun isSafeWhenDampened(report: Report): Boolean {
        for (i in 0..report.lastIndex) {
            val subset = report.filterIndexed { index, _ -> index != i }
            if (isSafe(subset)) {
                return true
            }
        }

        return false
    }

    private fun reports(): List<Report> {
        val lines = input().split("\n")

        return lines.map { report ->
            report.split(" ").map { level -> level.toInt() }
        }
    }
}
