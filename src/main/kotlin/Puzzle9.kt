package org.garethellis.adventofcode.twentyfour

class Puzzle9(input: String) : Puzzle(input) {
    override val part1ExampleSolution: Long = 1928
    override val part2ExampleSolution: Long = 2858

    override fun part1(): Long = createDisk().compact().checksum()
    override fun part2(): Long = createDisk().optimize().checksum()

    private fun createDisk(): Disk {
        val fileDescriptors = mutableListOf<Int>()
        val freeBlockDescriptors = mutableListOf<Int>()

        input().toCharArray().forEachIndexed { i, c ->
            if (i % 2 == 0) fileDescriptors.add(c.toString().toInt())
            else freeBlockDescriptors.add(c.toString().toInt())
        }

        val blocks = arrayOfNulls<FileBlock>(fileDescriptors.sum() + freeBlockDescriptors.sum())
        var blockIndex = 0

        for (i in fileDescriptors.indices) {
            // add n FileBlocks to blocks according to value of fileDescriptors[i]
            (1..fileDescriptors[i]).forEach { _ ->
                blocks[blockIndex] = FileBlock(i, fileDescriptors[i])
                blockIndex++
            }
            // skip m indices in blocks according to value of freeBlockDescriptors[i] (if set)
            blockIndex += freeBlockDescriptors.getOrElse(i) { 0 }
        }

        return Disk(blocks)
    }
}

class FileBlock(val id: Int, val totalFileLength: Int)

class Disk(private val blocks: Array<FileBlock?>) {

    private val freeBlockIndices: MutableList<Int> by lazy {
        blocks.mapIndexed { i, b -> if (b == null) i else -1 }.filter { it > -1 }.toMutableList()
    }

    private val fileBlockIndices: MutableList<Int> by lazy {
        blocks.mapIndexed { i, b -> if (b is FileBlock) i else -1 }.filter { it > -1 }.toMutableList()
    }

    fun print() {
        println(blocks.toList().map { if (it is FileBlock) it.id.toString() else '.' }.joinToString(""))
    }

    fun checksum(): Long = blocks.foldIndexed(0) { i, total, fileBlockOrNull ->
        if (fileBlockOrNull == null) return@foldIndexed total
        total + (i * fileBlockOrNull.id)
    }

    fun compact(): Disk {
        run breaking@{
            fileBlockIndices.reversed().forEach { fileBlockIndex ->
                val firstFreeBlockIndex = freeBlockIndices.first()
                if (fileBlockIndex < firstFreeBlockIndex) return@breaking

                move(fileBlockIndex, firstFreeBlockIndex)
            }
        }
        return this
    }

    fun optimize(): Disk {
        run breaking@{
            val movedFiles = mutableSetOf<Int>()
            val filesThatCantBeMoved = mutableSetOf<Int>()
            fileBlockIndices.reversed().forEach { fileBlockIndex ->
                val file = get(fileBlockIndex) ?: return@forEach

                if (file.id in movedFiles) return@forEach
                if (file.id in filesThatCantBeMoved) return@forEach

                val gapStart = findGapBefore(file.totalFileLength)
                if (gapStart == -1 || gapStart > fileBlockIndex) {
                    filesThatCantBeMoved.add(file.id)
                    return@forEach
                }

                (fileBlockIndex downTo (fileBlockIndex - file.totalFileLength + 1))
                    .forEachIndexed { i, it -> move(it, gapStart + i) }
                movedFiles.add(file.id)
            }
        }
        return this
    }

    private fun get(index: Int) = blocks[index]

    private fun findGapBefore(length: Int): Int {
        if (length == 1) return freeBlockIndices.first()

        freeBlockIndices.windowed(length).forEach {
            if (it.last() - it.first() == length - 1) return it.first()
        }

        return -1
    }

    private fun move(fromIndex: Int, toIndex: Int) {
        blocks[toIndex] = blocks[fromIndex].also { blocks[fromIndex] = null }

        fileBlockIndices.remove(fromIndex)

        freeBlockIndices.apply {
            remove(toIndex)
            add(fromIndex)
            sort()
        }
    }
}
