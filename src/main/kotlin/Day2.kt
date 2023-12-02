import java.io.File
import java.util.stream.Collectors

fun main() {
    val path = "src/main/resources/input2.txt"
    val file = File(path)
    val listOfCubes = mutableListOf<List<Map<String,Int>>>()
    file.forEachLine { line ->
        listOfCubes.add(parseLineToListOfMaps(line))
    }
    println("Part1: " + part1(listOfCubes))
    println("Part2: " + part2(listOfCubes))
}

fun parseLineToListOfMaps(line:String ): List<Map<String,Int>> {
    val game = (line.split(":")[0])
    val cubes = line.split(":")[1]
        .split(";")
        .map { it.trim().split(",") }

    val trimmedList = cubes.map { sublist -> sublist.map { it.trim().split(" ") } }
    val map = trimmedList.map { sublist ->
        sublist.associate { it[1] as String to it[0].toInt() }}
    return map
}


fun part1(list: MutableList<List<Map<String, Int>>>): Int {
    val redLimit = 12
    val greenLimit = 13
    val blueLimit = 14

    val toList = list.stream()
        .collect(Collectors.toList())
        .map { it to list.indexOf(it) }
        .map { flatMap(it)}
        .flatMap { it.entries }
        .filter { it.value.get("red")!! <= redLimit &&
                it.value.get("blue")!! <= blueLimit &&
                it.value.get("green")!! <= greenLimit }
        .map { it.key }
        .sum()
    return toList
}

fun flatMap(pair: Pair<List<Map<String,Int>>,Int>): Map<Int, Map<String, Int>> {
    val list = pair.first
    var maxRed = 0

    var maxGreen = 0
    var maxBlue = 0
    list.forEach {
        val currentRed = it.getOrElse("red") {0}
        if (maxRed < currentRed) maxRed = currentRed
        val currentBlue = it.getOrElse("blue") {0}
        if (maxBlue < currentBlue) maxBlue = currentBlue
        val currentGreen = it.getOrElse("green") {0}
        if (maxGreen < currentGreen) maxGreen = currentGreen
    }
    val map = mapOf("red" to maxRed,"blue" to maxBlue, "green" to maxGreen)
    return mapOf(pair.second +1 to map)
}

fun part2(list: MutableList<List<Map<String, Int>>>): Int {
    val toList = list.stream()
        .collect(Collectors.toList())
        .map { it to list.indexOf(it) }
        .map { flatMap(it)}
        .flatMap { it.entries }
        .map { it.value}
        .map {it.values.reduce {a,b -> a*b}}
        .sum()
    return toList;
}