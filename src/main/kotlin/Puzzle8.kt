package org.garethellis.adventofcode.twentyfour

typealias AntennaCode = Char
typealias AntennaPositions = Map<AntennaCode, Set<MapCoordinate>>
typealias MapCoordinate = Pair<Int, Int>
typealias MapRow = List<MapCell>
typealias MapCell = Char

class Puzzle8(input: String) : Puzzle(input) {
    override val part1ExampleSolution: Int = 14
    override val part2ExampleSolution: Int = 34

    override fun part1(): Int {
        val antennaMap = createAntennaMap()

        antennaMap.effectiveAntennaPositions().forEach { (_, positions) ->
            positions.forEach { a ->
                positions.forEach { b ->
                    antennaMap.addAntinode(antinodePositionFor(a, b))
                }
            }
        }

        return antennaMap.antinodes().count()
    }

    override fun part2(): Int {
        val antennaMap = createAntennaMap()

        antennaMap.effectiveAntennaPositions().forEach { (_, positions) ->
            positions.forEach { a ->
                positions.forEach { b ->
                    antinodePositionsWithResonantHarmonicsFor(a, b, antennaMap).map(antennaMap::addAntinode)
                }
            }
        }

        return antennaMap.antinodes().count()
    }

    private fun antinodePositionFor(antenna: MapCoordinate, collaborator: MapCoordinate): MapCoordinate {
        val (row, col) = antenna
        val (neighbourRow, neighbourCol) = collaborator

        val antinodeCoordinate = { rowOrCol: Int, neighbourRowOrCol: Int ->
            when {
                rowOrCol < neighbourRowOrCol -> neighbourRowOrCol + (neighbourRowOrCol - rowOrCol)
                rowOrCol > neighbourRowOrCol -> neighbourRowOrCol - (rowOrCol - neighbourRowOrCol)
                else -> -1
            }
        }

        return MapCoordinate(antinodeCoordinate(row, neighbourRow), antinodeCoordinate(col, neighbourCol))
    }

    private fun antinodePositionsWithResonantHarmonicsFor(
        antenna: MapCoordinate,
        collaborator: MapCoordinate,
        map: AntennaMap
    ): List<MapCoordinate> {
        val positions = mutableListOf(collaborator)

        var antinode = antinodePositionFor(antenna, collaborator)
        while (map.has(antinode)) {
            positions.add(antinode)

            antinode = antinodePositionFor(positions[positions.lastIndex - 1], positions.last())
        }

        return positions
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

    private fun createAntennaMap() = AntennaMap(input().split('\n').map { line -> line.toCharArray().toList() })
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

    fun addAntinode(coord: MapCoordinate) {
        if (has(coord)) antinodes.add(coord)
    }

    fun antinodes() = antinodes.toSet()

    fun has(coord: MapCoordinate): Boolean = rows.elementAtOrNull(coord.first)?.elementAtOrNull(coord.second) != null
}
