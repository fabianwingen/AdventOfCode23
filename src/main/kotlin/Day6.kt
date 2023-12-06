import java.io.File
import kotlin.math.*


fun main() {

    val path = "src/main/resources/input6.txt"
    val file = File(path)
    val lines = file.readLines().map { it.split(":")[1].trim().split(" ").filterNot { s -> s.isBlank() }.map { number -> number.toDouble() } }
    val timeToDistance = lines[0].mapIndexed { index, number -> number to lines[1][index] }
    println(part1(timeToDistance))
    println(part2(file))
}

private fun part1(timeToDistance: List<Pair<Double,Double>>): Int {
    return timeToDistance.map { calculatePair(it) }.reduce(){a,b -> a*b}
}

private fun calculatePair(pair: Pair<Double, Double>): Int {
        val time = pair.first.toDouble()
        val distance =pair.second.toDouble()
        val min =  ceil((-(-time)/2) - sqrt((-time/2).pow(2) - (distance+1)))
        val max =  (-(-time)/2) + sqrt((-time/2).pow(2) - (distance+1))
        return min.toInt().rangeTo(max.toInt()).toList().size
}

private fun part2(file: File): Int {
    val linesForPart2 = file.readLines().map { it.split(":")[1].trim()
        .split(" ")
        .filterNot { s->s.isBlank() }
        .reduce{a,b ->a.plus(b)}
    }

    return part1(listOf(Pair(linesForPart2[0].toDouble(),linesForPart2[1].toDouble())))
}
