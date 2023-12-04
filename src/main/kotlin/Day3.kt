import java.io.File


fun main() {
    val path = "src/main/resources/input3.txt"
    val file = File(path)
    println(part1(file))
    println(part2(file))
}

private fun part1(file: File): Int {
    val lines = file.readLines()
    var summe = 0;
    lines.forEach {
        var number = ""
        var numberHasSymbolAdjacent = false
        for (i in it.indices) {
            if (it[i].isDigit()) {
                number += it[i]
                if (!numberHasSymbolAdjacent) numberHasSymbolAdjacent = symbolAdjacent(lines, lines.indexOf(it), i)
            } else {
                if (numberHasSymbolAdjacent) {
                    summe += number.toInt()
                    numberHasSymbolAdjacent = false
                }
                number = ""
            }
        }
        if (numberHasSymbolAdjacent) { //in case number ends on the right border
            summe += number.toInt()
        }
    }
    return summe
}

private fun getSurroundingFields(lines: List<String>, x:Int, y:Int): List<Char> {

    val listOfAdjacentChars = mutableListOf<Char>()
    for (xIndex in x-1..x+1) {
        for (yIndex in y-1..y+1) {
            listOfAdjacentChars.add(lines.getOrElse(yIndex){"."}.getOrElse(xIndex) {'.'})
        }
    }
    return listOfAdjacentChars
}
data class Coordinates(val x:Int, val y:Int)

private fun symbolAdjacent(lines: List<String>, y: Int, x: Int): Boolean {
   val listOfFields = getSurroundingFields(lines, x, y)
    var valid = false
    listOfFields.forEach {
        if(!(it.isDigit() || it == '.')) {
            valid = true
        }
    }
    return valid
}

private fun findAStar(lines: List<String>, entry: Map.Entry<Coordinates, Int>): Coordinates? {
    val x = entry.key.x
    val y = entry.key.y
    val length = entry.value.toString().length
    for (l in 0 until length) {
        for (xIndex in x + l - 1..x + 1 + l) {
            for (yIndex in y - 1..y + 1) {
                val symbol = lines.getOrElse(yIndex) { "." }.getOrElse(xIndex) { '.' }
                if (symbol == '*') return Coordinates(xIndex, yIndex)
            }
        }
    }
    return null
}

private fun mapCoordinatesToNumbers(file: File): Map<Coordinates, Int> {
    val lines = file.readLines()
    val numbers = mutableMapOf<Coordinates,Int>()
    lines.forEachIndexed { index, line ->
        val number = "\\d+".toRegex()
        val numbersInLine = number.findAll(line).map {
            Coordinates(it.range.first,index) to it.value.toInt()
        }.toMap()
        numbers.putAll(numbersInLine)
    }
    return numbers
}

private fun part2(file: File): Int {
    val lines = file.readLines()
    val mapCoordinatesToNumbers = mapCoordinatesToNumbers(file)
    val mapFromCoordsOfNumberToStar = mapCoordinatesToNumbers.asSequence()
        .filter { findAStar(lines, it) != null }
        .map { it to findAStar(lines, it) }
        .toMap()

    val mapFromStarToNumbers = mapFromCoordsOfNumberToStar.asSequence().groupBy(keySelector = { it.value },
        valueTransform = { it.key })
        .filter { it.value.size >= 2 }
        .map { it.key to it.value.map { entry -> entry.value }}
        .map { it.first to it.second.reduce(){a,b -> a*b} }
        .map { it.second }
        .sum()
    return mapFromStarToNumbers
}


