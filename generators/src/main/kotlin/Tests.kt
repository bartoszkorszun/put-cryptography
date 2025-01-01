fun testBit(bitSeq: String): Boolean {
    val countOnes = bitSeq.count { it == '1' }
    return countOnes in 9725..10275
}

fun testSeries(bitSeq: String): Boolean {
    val ranges = mapOf(
        1 to (2315 to 2685),
        2 to (1114 to 1386),
        3 to (527 to 723),
        4 to (240 to 384),
        5 to (103 to 209),
        6 to (103 to 209)
    )

    fun countSeries(bitSeq: String, value: Char): Map<Int, Int> {
        val result = mutableMapOf(
            1 to 0,
            2 to 0,
            3 to 0,
            4 to 0,
            5 to 0,
            6 to 0
        )
        var i = 0

        while (i < bitSeq.length) {
            var count = 0
            while (i + count < bitSeq.length && bitSeq[i + count] == value) {
                count++
            }
            if (count > 0) {
                val seriesLength = if (count > 6) 6 else count
                result[seriesLength] = result[seriesLength]!! + 1
                i += count
            } else {
                i++
            }
        }
        return result
    }

    val seriesCounts = countSeries(bitSeq, '1')
    for ((length, count) in seriesCounts) {
        val (min, max) = ranges[length]!!
        if (count !in min..max) {
            return false
        }
    }

    return true
}

fun testLongSeries(bitSeq: String): Boolean {
    for (i in 0 until bitSeq.length - 26) {
        if (
            bitSeq.substring(i, i + 26).all { it == '1' } ||
            bitSeq.substring(i, i + 26).all { it == '0' }
        ) {
            return false
        }
    }
    return true
}

fun testPoker(bitSeq: String): Boolean {
    val counts = mutableMapOf<String, Int>()
    for (i in 0 until bitSeq.length - 4 step 4) {
        val subSeq = bitSeq.substring(i, i + 4)
        counts[subSeq] = counts.getOrDefault(subSeq, 0) + 1
    }

    val sum = counts.values.sum()
    val x = counts.values.sumOf { it * it }
    val poker = 16.0 / 5000 * x - 5000

    return poker in 2.16..46.17
}