import java.io.File

fun main() {
    val path = "src/main/resources/input2.txt"
    val file = File(path)
    val list = mutableListOf<String>()
    println(part1(file))
    print(part2(file))
}

private fun findAnzahlOfColor(line: String, color: String): Int {
    return Regex("\\d+(?= $color)").findAll(line).toList().maxOfOrNull { it.value.toInt() } ?: 0
}

private fun part1(file: File): Int {
    return file.readLines()
        .asSequence()
        .mapIndexed { index, m -> listOf("red","blue","green").associateWith { color -> findAnzahlOfColor(m,color) } to index+1}
        .filter { it.first["red"]!! <= 12 && it.first["green"]!! <= 13 && it.first["blue"]!! <= 14 }
        .map { it.second }
        .sum()

}

private fun part2(file: File): Int {
    return file.readLines()
        .asSequence()
        .mapIndexed { index, m -> listOf("red","blue","green").associateWith { color -> findAnzahlOfColor(m,color) } to index+1}
        .map { it.first.values.reduce {a,b -> a*b } }
        .sum()
}