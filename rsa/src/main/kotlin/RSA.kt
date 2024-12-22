import java.security.SecureRandom
import java.math.BigInteger
import kotlin.math.*

fun gcd(a: Long, b: Long): Int {
    var x = a
    var y = b
    while (y != 0) {
        val t = y
        y = x % y
        x = t
    }
    return x
}

fun generateE(p: Int, q: Int): Int {
    val phi = (BigInteger.valueOf(p.toLong()) - BigInteger.ONE) * (BigInteger.valueOf(q.toLong()) - BigInteger.ONE).toLong()
    println("phi = $phi")
    val secureRandom = SecureRandom()
    while (true) {
        val e = secureRandom.nextInt(phi.toInt())
        if (e > 1 && e < phi && e.gcd(phi) == 1) {
            return e
        }
    }
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

fun main() {
    val (p, q) = generatePrimes(31)
    val e = generateE(p, q)

    println("p = $p\nq = $q\ne = $e")
}