import java.security.SecureRandom
import java.math.BigInteger
import kotlin.math.*

fun encryptDecrypt(input: Long, ed: Long, n: Long): Long {
    return BigInteger.valueOf(input).modPow(BigInteger.valueOf(ed), BigInteger.valueOf(n)).toLong()
}

fun gcd(a: Long, b: Long): Long {
    var x = a
    var y = b
    while (y != 0L) {
        val t = y
        y = x % y
        x = t
    }
    return x
}

fun generateED(p: Int, q: Int): Pair<Long, Long> {
    val phi = (p.toLong() - 1L) * (q.toLong() - 1L)
    println("phi = $phi")
    val secureRandom = SecureRandom()
    var e: Long
    while (true) {
        e = secureRandom.nextLong(phi)
        if (e > 1 && e < phi && gcd(e, phi) == 1L) {
            break
        }
    }
    
    val d = BigInteger.valueOf(e).modInverse(BigInteger.valueOf(phi)).toLong()

    return Pair(e, d)
}

fun generateN(p: Int, q: Int): Long {
    return p.toLong() * q.toLong()
}

fun isPrime(number: Int): Boolean {
    if (number < 2) return false
    for (i in 2..sqrt(number.toDouble()).toInt()) {
        if (number % i == 0) return false
    }
    return true
}

fun generatePrimes(bits: Int): Pair<Int, Int> {
    val secureRandom = SecureRandom()
    while (true) {
        val p = secureRandom.nextInt((1 shl bits) - 1) + (1 shl (bits - 1))
        if (isPrime(p)) {
            val q = secureRandom.nextInt((1 shl bits) - 1) + (1 shl (bits - 1))
            if (isPrime(q) && p != q) {
                return Pair(p, q)
            }
        }
    }
}