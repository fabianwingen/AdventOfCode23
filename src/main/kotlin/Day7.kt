import java.io.File

fun main() {

    val path = "src/main/resources/input7.txt"
    val file = File(path)
    val map = file.readLines().map { it.split(" ") }.associate { it[0] to it[1].toInt() }
    println(part1Und2(map,1))
    println(part1Und2(map,2))
}

private fun part1Und2(map: Map<String, Int>,part: Int): Int {
    return map.map { Hand(it.key,part) to it.value  }
        .toList()
        .sortedBy { it.first }
        .mapIndexed { index, hand -> hand.second * (index+1)}
        .sum()
}

private data class Hand(val hand: String, val part: Int): Comparable<Hand> {

    private val cards: String
        get() = if (part == 1) "AKQJT98765432" else "AKQT98765432J"

    private val type: Int
        get() = if (part == 1) this.hand.getType() else this.hand.getType2()

    private val charToStrength: Map<Char,Int>
        get() = cards.reversed().mapIndexed {index, c -> c to index }.toMap()

    override fun compareTo(other: Hand): Int {
        val typeComparison  = this.type.compareTo(other.type)
        if (typeComparison != 0) {return typeComparison}
        return this.hand.zip(other.hand).find { it.first != it.second }
            ?.let { charToStrength[it.first]!!.compareTo(charToStrength[it.second]!!) }
            ?: 0
    }

}

private fun typeStrength(appearancesOfSameChars: List<Int>): Int {
    return when (appearancesOfSameChars) { //Fehler
        listOf(5) -> 7
        listOf(4,1) -> 6
        listOf(3,2) -> 5
        listOf(3,1,1) -> 4
        listOf(2,2,1) -> 3
        listOf(2,1,1,1) -> 2
        listOf(1,1,1,1,1) -> 1
        else -> 0
    }
}

private fun String.mapCharToAppearance(): List<Int> {
    return this.groupingBy { it }.eachCount().values.sorted().reversed()
}

private fun String.getType(): Int {
    return typeStrength(this.mapCharToAppearance())
}

private fun String.getType2(): Int {
    return "AKQJT98765432".map { typeStrength(this.replace('J',it).mapCharToAppearance()) }.max()
}






