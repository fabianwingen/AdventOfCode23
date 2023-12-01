import java.io.File

fun main() {
    val path = "src/main/resources/input1.txt"
    val file = File(path)
    part1(file)
}

fun part1(file: File) {
    var summe = 0
    file.forEachLine { line ->
        summe += getNumber(line)
    }
    print(summe)
}
fun getNumber(line: String): Int {
    var chars: String = ""
    for (c in line) {
        if ( c.isDigit()) {
            chars += c
        }
    }
    val number = "" + chars.first() + chars.last()
    return number.toInt()

}
