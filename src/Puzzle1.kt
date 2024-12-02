import java.io.File
import kotlin.math.absoluteValue

class Puzzle1 : Puzzle {
    private fun lists(): Pair<List<Int>, List<Int>> {
        val input = File("./input/puzzle1").readText()

        val lines = input.split("\n").filter { it.isNotBlank() }

        var list1 : List<Int> = listOf()
        var list2 : List<Int> = listOf()
        lines.forEach {
            val nums = it.split("   ")

            list1 += nums[0].toInt()
            list2 += nums[1].toInt()
        }

        return Pair(list1, list2)
    }

    override fun part1() : Int {
        val (list1, list2) = lists()

        val sortedList1 = list1.sorted()
        val sortedList2 = list2.sorted()

        var totalDiff = 0
        for (i in 0.. sortedList1.lastIndex) {
            totalDiff += (sortedList1[i] - sortedList2[i]).absoluteValue
        }

        return totalDiff
    }

    override fun part2() : Int {
        val (list1, list2) = lists()

        var similarityScore = 0
        list1.forEach { it1 ->
            val countInList2 = list2.filter { it2 -> it2 == it1 }.size
            similarityScore += it1 * countInList2
        }

        return similarityScore
    }
}
