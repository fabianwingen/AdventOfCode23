import java.io.File

fun main() {

    val path = "src/main/resources/input9.txt"
    val file = File(path)
    val OASIS = file.readLines().map { it.split(" ").map { s -> s.toInt() } }
    println(part1(OASIS))
    println(part2(OASIS))
}

private fun part2(report: List<List<Int>>): Any {
    return part1(report.map { it.reversed() })
}

private fun part1(report: List<List<Int>>): Int {
    return report.map { extrapolate(getDifferences(it.toList())) }.sumOf { it.last() }
}

private fun  getDifferences(ints: List<Int>): List<List<Int>> {
    val differences = ints.zipWithNext { current, next -> next - current}
    return if (differences.all { it == 0 }) {
        listOf(ints, differences)
    }
    else listOf(ints) + getDifferences(differences)
}

private fun extrapolate(lists: List<List<Int>>): List<Int> {
    return lists[0] + calculateNextNumber(lists, 0)
}

private fun calculateNextNumber(list: List<List<Int>>, index: Int): Int {
    return if (list[index].all { it == 0 }) {
        0
    } else list[index].last() + calculateNextNumber(list, index+1)
}




