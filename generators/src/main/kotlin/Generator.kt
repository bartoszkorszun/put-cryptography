fun main() {
    val bitSeqLen = 20_000
    
    while(true) {
        val bitSeq = generateSeq(bitSeqLen)
        if (
            testBit(bitSeq) &&
            testSeries(bitSeq) &&
            testLongSeries(bitSeq) &&
            testPoker(bitSeq)
        ) {
            println("All tests passed for bit sequence: \n$bitSeq")
            break
        }
    }
}