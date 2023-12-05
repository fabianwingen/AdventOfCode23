import java.io.File
import kotlin.math.pow

fun main() {
    val path = "src/main/resources/input4.txt"
    val file = File(path)
    val winningNumbers = extractNumbers(file, 0)
    val myNumbers = extractNumbers(file,1)
    println("Part1: ${part1(winningNumbers, myNumbers)}")
    println("Part2: ${part2(winningNumbers, myNumbers)}")
}

private fun extractNumbers(file: File, part: Int): List<Set<Int>> {
    return file.readLines().asSequence()
        .map { it.split(":")[1].split(" |")[part] }
        .map { "\\d+".toRegex().findAll(it).map { it.value.toInt() }.toSet() }
        .toList()
}

private fun part1(winningNumbers: List<Set<Int>>, myNumbers: List<Set<Int>>): Int {

    return myNumbers.mapIndexed { index, subset ->
        val size = subset.asSequence()
            .filter { winningNumbers[index].contains(it) }
            .toList()
            .size
        if (size > 0) 2.0.pow(size-1).toInt() else 0
    }.sum()
}

private fun part2(winningNumbers: List<Set<Int>>, myNumbers: List<Set<Int>>): Int {

    val games = mutableMapOf<Int, Int>()
    for (i in 1..winningNumbers.size) {
        games[i] = 1
    }
    val winningAmounts = myNumbers.mapIndexed { index, subset ->
        subset.asSequence()
            .filter { winningNumbers[index].contains(it) }
            .toList()
            .size
    }
    winningAmounts.forEachIndexed() {
        index, number ->
        for (j in 1..games[index+1]!!) {
            for (i in 1..number) {
                games[index + 1 + i] = games[index + 1 + i]?.plus(1) ?: 0
            }
        }
    }
    return games.values.sum()
}