package org.garethellis.adventofcode.twentyfour

import kotlin.math.absoluteValue

class Puzzle1(inputFile: String) : Puzzle(inputFile) {
    override fun part1(): Int {
        val (list1, list2) = lists()

        val sortedList1 = list1.sorted()
        val sortedList2 = list2.sorted()

        return sortedList1.foldIndexed(0) { i, totalDiff, n ->
            totalDiff + (n - sortedList2[i]).absoluteValue
        }
    }

    override fun part2(): Int {
        val (list1, list2) = lists()

        return list1.foldIndexed(0) { i, similarityScore, n ->
            val countInList2 = list2.count { it == n }
            similarityScore + n * countInList2
        }
    }

    private fun lists(): Pair<List<Int>, List<Int>> {
        val lines = input().split("\n")

        val list1: MutableList<Int> = mutableListOf()
        val list2: MutableList<Int> = mutableListOf()
        lines.forEach {
            val (a, b) = it.split("   ")

            list1 += a.toInt()
            list2 += b.toInt()
        }

        return Pair(list1.toList(), list2.toList())
    }
}
