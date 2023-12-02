import java.io.File

fun main() {
    val path = "src/main/resources/input1.txt"
    val file = File(path)
    part1(file)
    part2(file)
}

private fun part1(file: File) {
    var summe = 0
    file.forEachLine { line ->
        summe += getDigits(line)
    }
    println("Part1: " +summe)
}
private fun getDigits(line: String): Int {
    var chars: String = ""
    for (c in line) {
        if ( c.isDigit()) {
            chars += c
        }
    }
    val number = "" + chars.first() + chars.last()
    return number.toInt()

}
private fun part2(file: File) {
    var summe = 0
    file.forEachLine { line ->
        summe += getSum(line)
    }
    print("Part2: " + summe)
}
private fun getSum(line: String): Int {
    val textToDigit = mapOf(
        "one" to "1", "two" to "2", "three" to "3", "four" to "4", "five" to "5", "six" to "6",
        "seven" to "7", "eight" to "8", "nine" to "9", "1" to "1", "2" to "2", "3" to "3",
        "4" to "4", "5" to "5", "6" to "6", "7" to "7", "8" to "8", "9" to "9")
    val frontSubstrings = mutableListOf<String>()
    for (i in 1..line.length) {
        frontSubstrings.add(line.substring(0,i));
    }

    val backSubstrings = mutableListOf<String>()
    for (i in 1..line.length) {
        backSubstrings.add(line.substring(line.length - i))
    }

    val firstSubstringContainingADigit = frontSubstrings.find { substring -> textToDigit.keys.any { substring.contains(it)} }
    val lastSubstringContainingADigit = backSubstrings.find { substring -> textToDigit.keys.any{ substring.contains(it)} }
    val firstDigit = textToDigit.keys.find { firstSubstringContainingADigit!!.contains(it) }
    val lastDigit = textToDigit.keys.find { lastSubstringContainingADigit!!.contains(it) }

    val summe = "" + textToDigit.get(firstDigit) + textToDigit.get(lastDigit)
    return summe.toInt()
}


