import java.security.SecureRandom
import java.math.BigInteger
import kotlin.math.*

fun generatePrime(bits: Int): Int {
    val secureRandom = SecureRandom()
    while (true) {
        val candidate = secureRandom.nextInt((1 shl bits) - 1) + (1 shl (bits - 1))
        if (isPrime(candidate)) {
            return candidate
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

fun isPrimitiveRoot(g: Int, n: Int): Boolean {
    val phi = n - 1
    val factors = primeFactors(phi)
    for (factor in factors) {
        if (BigInteger.valueOf(g.toLong())
                .modPow(BigInteger.valueOf((phi / factor).toLong()), BigInteger.valueOf(n.toLong()))
                == BigInteger.ONE) {
            return false
        }
    }
    return true
}

fun primeFactors(n: Int): List<Int> {
    val factors = mutableListOf<Int>()
    var num = n
    var i = 2
    while (i * i <= num) {
        if (num % i == 0) {
            factors.add(i)
            while (num % i == 0) {
                num /= i
            }
        }
        i++
    }
    if (num > 1) {
        factors.add(num)
    }
    return factors
}

fun findPrimitiveRoot(n: Int): Int {
    val random = SecureRandom()
    while (true) {
        val g = random.nextInt(n - 1000000) + 1000000
        if (isPrimitiveRoot(g, n)) {
            return g
        }
    }
}