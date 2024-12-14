import java.security.SecureRandom
import kotlin.math.sqrt

fun main() {
    val n = generatePrime(31) 
    val g = findPrimitiveRoot(n)

    println("Prime number: $n \nPrimitive root: $g")
}

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

fun findPrimitiveRoot(n: Int): Int {
    val phi = n - 1
    val primeFactors = findPrimeFactors(phi)

    for (candidate in n - 999 downTo 2) {
        var isPrimitiveRoot = true
        for (factor in primeFactors) {
            if (modularExponentiation(candidate, phi / factor, n) == 1) {
                isPrimitiveRoot = false
                break
            }
        }
        if (isPrimitiveRoot) {
            return candidate
        }
    }
    return -1 
}

fun findPrimeFactors(n: Int): List<Int> {
    val factors = mutableListOf<Int>()
    var number = n
    var divisor = 2
    while (number >= 2) {
        if (number % divisor == 0) {
            factors.add(divisor)
            while (number % divisor == 0) {
                number /= divisor
            }
        }
        divisor++
    }
    return factors
}

fun modularExponentiation(base: Int, exponent: Int, mod: Int): Int {
    var result = 1
    var b = base % mod
    var e = exponent
    while (e > 0) {
        if (e % 2 == 1) {
            result = (result * b) % mod
        }
        e = e shr 1
        b = (b * b) % mod
    }
    return result
}