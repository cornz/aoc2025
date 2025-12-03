fun main() {

    fun maxBankJoltage(bank: String, pick: Int): Long {
        val digits = bank.trim()
        if (digits.length <= pick) return digits.toLong()

        var dropsLeft = digits.length - pick
        val stack = ArrayDeque<Char>()

        for (ch in digits) {
            while (dropsLeft > 0 && stack.isNotEmpty() && stack.last() < ch) {
                stack.removeLast()
                dropsLeft--
            }
            stack.addLast(ch)
        }

        while (stack.size > pick) stack.removeLast()

        val result = StringBuilder(pick)
        stack.forEach { result.append(it) }
        return result.toString().toLong()
    }

    fun part1(input: List<String>): Long = input
        .filter { it.isNotBlank() }
        .sumOf { maxBankJoltage(it, 2) }

    fun part2(input: List<String>): Long = input
        .filter { it.isNotBlank() }
        .sumOf { maxBankJoltage(it, 12) }

    val testInput = readInput("input/Day03_test")
    check(part1(testInput) == 357L)
    check(part2(testInput) == 3121910778619L)

    val input = readInput("input/Day03")
    part1(input).println()
    part2(input).println()
}
