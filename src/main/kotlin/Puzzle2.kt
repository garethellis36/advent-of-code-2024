package org.garethellis.adventofcode.twentyfour

import kotlin.math.absoluteValue

typealias Level = Int
typealias Report = List<Level>

class Puzzle2(inputFile: String) : Puzzle(inputFile) {
    override fun part1(): Int {
        return reports().count(::isSafe)
    }

    override fun part2(): Int {
        val (safe, unsafe) = reports().partition(::isSafe)

        return safe.count() + unsafe.count(::isSafeWhenDampened)
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

            val diff = (report[i] - report[i + 1]).absoluteValue
            if (diff < 1 || diff > 3) {
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
        return input()
            .split("\n")
            .map { report ->
                report.split(" ").map { level -> level.toInt() }
            }
    }
}
