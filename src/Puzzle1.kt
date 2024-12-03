import kotlin.math.absoluteValue

class Puzzle1 : Puzzle {
    override fun puzzleNumber(): Int {
        return 1
    }

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
            val countInList2 = list2.filter { it == n }.count()
            similarityScore + n * countInList2
        }
    }

    private fun lists(): Pair<List<Int>, List<Int>> {
        val lines = input().split("\n")

        val list1: MutableList<Int> = mutableListOf()
        val list2: MutableList<Int> = mutableListOf()
        lines.forEach {
            val nums = it.split("   ")

            list1 += nums[0].toInt()
            list2 += nums[1].toInt()
        }

        return Pair(list1.toList(), list2.toList())
    }
}
