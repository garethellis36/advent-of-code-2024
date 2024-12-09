package org.garethellis.adventofcode.twentyfour

typealias AntennaCode = Char
typealias AntennaPositions = Map<AntennaCode, Set<MapCoordinate>>
typealias MapCoordinate = Pair<Int, Int>
typealias MapRow = List<MapCell>
typealias MapCell = Char

class Puzzle8(input: String) : Puzzle(input) {
    override val part1ExampleSolution: Int = 14
    override val part2ExampleSolution: Int = 0

    override fun part1(): Int {
        val map = createMap()

        map.effectiveAntennaPositions().forEach { (_, positions) ->
            positions.forEachIndexed { i, a ->
                positions.forEachIndexed { j, b ->
                    if (j > i) map.addAntinodes(antinodePositionsFor(a, b))
                }
            }
        }

        return map.antinodes().count()
    }

    private fun draw(antinodes: Set<MapCoordinate>) {
        println(input().split('\n').mapIndexed { row, line ->
            line.toCharArray().mapIndexed inner@{ cell, c ->
                if (c == '.' && antinodes.contains(MapCoordinate(row, cell))) return@inner '#'
                if (antinodes.contains(MapCoordinate(row, cell))) return@inner '%'
                c
            }.joinToString("")
        }.joinToString("\n"))
    }

    private fun createMap() = AntennaMap(input().split('\n').map { line -> line.toCharArray().toList() })

    override fun part2(): Int {
        return -1
    }

    private fun antinodePositionsFor(a: MapCoordinate, b: MapCoordinate): Pair<MapCoordinate, MapCoordinate> {
        return Pair(
            antinodePositionFor(a, b),
            antinodePositionFor(b, a)
        )
    }

    private fun antinodePositionFor(pos: MapCoordinate, collaborator: MapCoordinate): MapCoordinate {
        val (row, col) = pos
        val (neighbourRow, neighbourCol) = collaborator

        return MapCoordinate(
            when {
                row < neighbourRow -> neighbourRow + (neighbourRow - row)
                row > neighbourRow -> neighbourRow - (row - neighbourRow)
                else -> -1
            },
            when {
                col < neighbourCol -> neighbourCol + (neighbourCol - col)
                col > neighbourCol -> neighbourCol - (col - neighbourCol)
                else -> -1
            }
        )
    }
}

class AntennaMap(private val rows: List<MapRow>) {
    private var antinodes = mutableSetOf<MapCoordinate>()

    fun effectiveAntennaPositions(): AntennaPositions {
        val positions = mutableMapOf<AntennaCode, Set<MapCoordinate>>()
        rows.forEachIndexed { rIndex, cells ->
            cells.forEachIndexed charForEach@{ cIndex, char ->
                val p = MapCoordinate(rIndex, cIndex)
                if (char == '.') return@charForEach
                positions[char] = if (positions.containsKey(char)) positions[char]!! + p else mutableSetOf(p)
            }
        }
        return positions
            .toMap()
            // an antenna with only one position will not have any anti-nodes
            .filter { (_, coords) -> coords.count() > 1 }
    }

    fun addAntinodes(coords: Pair<MapCoordinate, MapCoordinate>) {
        addAntinode(coords.first)
        addAntinode(coords.second)
    }

    private fun addAntinode(coord: MapCoordinate) {
        if (has(coord)) antinodes.add(coord)
    }

    fun antinodes() = antinodes.toSet()

    private fun has(coord: MapCoordinate): Boolean {
        return rows.elementAtOrNull(coord.first)?.elementAtOrNull(coord.second) != null
    }
}
