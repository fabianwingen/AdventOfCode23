import java.io.File


fun main() {
    val path = "src/main/resources/input3.txt"
    val file = File(path)
    println(part1(file))
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

private fun symbolAdjacent(lines: List<String>, y: Int, x: Int): Boolean {
    val topLeft = lines.getOrElse(y-1){"."}.getOrElse(x-1) {'.'}
    val topMiddle = lines.getOrElse(y-1){"."}.getOrElse(x){'.'}
    val topRight = lines.getOrElse(y-1){"."}.getOrElse(x+1){'.'}
    val left = lines.getOrElse(y){"."}.getOrElse(x-1){'.'}
    val right = lines.getOrElse(y){"."}.getOrElse(x+1){'.'}
    val bottomLeft = lines.getOrElse(y+1){"."}.getOrElse(x-1){'.'}
    val bottomMiddle = lines.getOrElse(y+1){"."}.getOrElse(x){'.'}
    val bottomRight = lines.getOrElse(y+1){"."}.getOrElse(x+1){'.'}

    val conditions = listOf(topLeft,topMiddle,topRight,right,left,bottomLeft,bottomMiddle,bottomRight)
    var valid = false
    conditions.forEach {
        if(!(it.isDigit() || it == '.')) {
            valid = true
        }
    }
    return valid
}
