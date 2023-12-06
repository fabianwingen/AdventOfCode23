import java.io.File

fun main() {
    val path = "src/main/resources/input5.txt"
    val file = File(path)
    val seeds = file.readLines()[0].split(": ")[1].split(" ").map { it.toLong() }
    val mapLists = parseInputToMaps(file)
    println(part1(seeds,mapLists))
    println(part2(file, mapLists))
}

private fun part1(seeds: List<Long>,mapLists: List<List<List<Long>>>): Long {

    return seeds.asSequence().map { mapLists.fold(it) { current, sublist -> sublist
        .asSequence()
        .filter { list -> current in list[1] until list[1]+list[2] }
        .ifEmpty { sequenceOf(listOf(current,current,1)) }
        .map { list -> list[0] + (current - list[1]) }
        .first() } }.min()

}

private fun part2(file: File, mapLists: List<List<List<Long>>>): Long { //TODO: TOO SLOW
    val seeds = file.readLines()[0].split(": ")[1].split(" ").map { it.toLong() }
    val seeds2 = seeds.chunked(2) //.map { el -> it[0]+el }}.reduce(){a,b -> a.plus(b)}
    //println(part1(seeds2,parseInputToMaps(file)))

    var min = part1(listOf(seeds[0]),mapLists)
    seeds2.forEach { for ( i in 0..it.last()) {
        val result = part1(listOf(it.first()+i,it.first()+it.last()+1-i),mapLists)
        if (result < min ) { min = result}
        }
    }
    return min;
}

private fun parseInputToMaps(file: File): List<List<List<Long>>> {
    val maps = file.readText().split("\n\n(?=\\w)".toRegex())

    val mapLists = maps.map { it.lines()
                                        .filterNot { line -> line.endsWith("map:") }
                            }.filterNot { it.first().startsWith("seeds") }
                            .map {
                                    it.map { string -> string.split(" ")
                                                                .map { el -> el.toLong() }
                                    }
                            }.filterNot { sublist -> sublist.isEmpty() }

    return mapLists
}


