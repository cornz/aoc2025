fun main() {

    fun parseRanges(input: List<String>): List<LongRange> = input
        .flatMap { line -> line.split(",") }
        .mapNotNull { token ->
            val trimmed = token.trim()
            if (trimmed.isEmpty()) return@mapNotNull null
            val (start, end) = trimmed.split("-").map { it.toLong() }
            start..end
        }

    fun findInvalidIds(input: List<String>, minRepeats: Int, maxRepeats: Int? = null): Set<Long> {
        val ranges = parseRanges(input)
        val maxUpper = ranges.maxOf { it.last }
        val maxDigits = maxUpper.toString().length

        fun pow10(length: Int): Long {
            var value = 1L
            repeat(length) { value *= 10 }
            return value
        }

        fun isInAnyRange(value: Long): Boolean = ranges.any { value in it }

        return buildSet {
            for (blockLen in 1..maxDigits) {
                val baseStart = if (blockLen == 1) 1 else pow10(blockLen - 1)
                val baseEnd = pow10(blockLen) - 1
                val repeatUpper = maxRepeats?.coerceAtMost(maxDigits / blockLen) ?: (maxDigits / blockLen)

                for (repeatCount in minRepeats..repeatUpper) {
                    val totalLen = blockLen * repeatCount
                    if (totalLen > maxDigits) break

                    for (base in baseStart..baseEnd) {
                        val repeated = base.toString().repeat(repeatCount).toLong()
                        if (repeated > maxUpper) break
                        if (isInAnyRange(repeated)) add(repeated)
                    }
                }
            }
        }
    }

    fun part1(input: List<String>): Long = findInvalidIds(input, minRepeats = 2, maxRepeats = 2).sum()

    fun part2(input: List<String>): Long = findInvalidIds(input, minRepeats = 2).sum()

    val testInput = readInput("input/Day02_test")
    check(part1(testInput) == 1227775554L)
    check(part2(testInput) == 4174379265L)

    val input = readInput("input/Day02")
    part1(input).println()
    part2(input).println()
}
