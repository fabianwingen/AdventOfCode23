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
        var valid = false
        for (i in it.indices) {
            if (it[i].isDigit()) {
                number += it[i]
                if (!valid) valid = symbolAdjacent(lines, lines.indexOf(it), i)
            } else {
                if (valid) {
                    summe += number.toInt()
                    valid = false
                }
                number = ""
            }
        }
        if (valid) { //in case number ends on the right border
            summe += number.toInt()
        }
    }
    return summe
}

private fun getSurroundingFields(lines: List<String>, x:Int, y:Int): List<Char> {

    val topLeft = lines.getOrElse(y-1){"."}.getOrElse(x-1) {'.'}
    val topMiddle = lines.getOrElse(y-1){"."}.getOrElse(x){'.'}
    val topRight = lines.getOrElse(y-1){"."}.getOrElse(x+1){'.'}
    val left = lines.getOrElse(y){"."}.getOrElse(x-1){'.'}
    val right = lines.getOrElse(y){"."}.getOrElse(x+1){'.'}
    val bottomLeft = lines.getOrElse(y+1){"."}.getOrElse(x-1){'.'}
    val bottomMiddle = lines.getOrElse(y+1){"."}.getOrElse(x){'.'}
    val bottomRight = lines.getOrElse(y+1){"."}.getOrElse(x+1){'.'}

    return listOf(topLeft,topMiddle,topRight,right,left,bottomLeft,bottomMiddle,bottomRight)

}
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
data class Coordinates(val x:Int, val y:Int)
private fun findAStar(lines: List<String>, x:Int, y:Int, length:Int): Coordinates? {
    for (l in 0..length-1) {
        for (xIndex in x + l - 1..x + 1 + l) {
            for (yIndex in y - 1..y + 1) {
                val symbol = lines.getOrElse(yIndex) { "." }.getOrElse(xIndex) { '.' }
                if (symbol == '*') return Coordinates(xIndex, yIndex)
            }
        }
    }
    return null
}
private fun part2(file: File): Int {
    val lines = file.readLines()
    val mapCoordinatesToNumbers = mapCoordinatesToNumbers(file)
    val mapFromCoordsOfNumberToStar = mapCoordinatesToNumbers.asSequence()
        .filter { findAStar(lines, it.key.x, it.key.y,it.value.toString().length) != null }
        .map { it to findAStar(lines, it.key.x, it.key.y, it.value.toString().length) }
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
