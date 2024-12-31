import java.security.SecureRandom

fun generatePrime(bits: Int): Int {
    val secureRandom = SecureRandom()
    while (true) {
        val candidate = secureRandom.nextInt((1 shl bits) - 1) + (1 shl (bits - 1))
        if (candidate % 4 == 3) {
            return candidate
        }
    }
}

fun generateSeq(length: Int): String {
    val p = generatePrime(31)
    val q = generatePrime(31)
    val n = p.toLong() * q.toLong()
    println("p = $p, q = $q, n = $n")
    return "dupeczka"
}