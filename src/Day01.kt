fun main() {
    fun wrapToDial(value: Int): Int {
        var wrapped = value
        while (wrapped >= 100) wrapped -= 100
        while (wrapped < 0) wrapped += 100
        return wrapped
    }

    fun part1(input: List<String>): Int {
        var position = 50
        var zeroHits = 0

        input.filter { it.isNotBlank() }.forEach { line ->
            val direction = line.first()
            val distance = line.drop(1).toInt()

            position = when (direction) {
                'R' -> wrapToDial(position + distance)
                'L' -> wrapToDial(position - distance)
                else -> error("Unknown rotation: $line")
            }

            if (position == 0) {
                zeroHits++
            }
        }

        return zeroHits
    }

    fun part2(input: List<String>): Int {
        var position = 50
        var zeroHits = 0

        input.filter { it.isNotBlank() }.forEach { line ->
            val direction = line.first()
            val distance = line.drop(1).toInt()

            val hitsThisTurn = when (direction) {
                'R' -> {
                    val firstHit = if (position == 0) 100 else 100 - position
                    if (firstHit > distance) 0 else 1 + (distance - firstHit) / 100
                }
                'L' -> {
                    val firstHit = if (position == 0) 100 else position
                    if (firstHit > distance) 0 else 1 + (distance - firstHit) / 100
                }
                else -> error("Unknown rotation: $line")
            }

            zeroHits += hitsThisTurn

            position = when (direction) {
                'L' -> wrapToDial(position - distance)
                'R' -> wrapToDial(position + distance)
                else -> error("Unknown rotation: $line")
            }
        }

        return zeroHits
    }

    val testInput = readInput("input/Day01_test")
    check(part1(testInput) == 3)
    check(part2(testInput) == 6)

    val input = readInput("input/Day01")
    part1(input).println()
    part2(input).println()
}
