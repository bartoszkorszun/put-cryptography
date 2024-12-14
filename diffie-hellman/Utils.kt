import java.security.SecureRandom
import kotlin.math.*

fun generatePrime(bits: Int): Double {
    val secureRandom = SecureRandom()
    val lowerBound = 1.0 shl (bits - 1)
    val upperBound = (1 shl bits) - 1.0

    while (true) {
        val candidate = secureRandom.nextDouble(upperBound - lowerBound) + lowerBound
        if (isPrime(candidate)) {
            return candidate
        }
    }
}

fun isPrime(number: Double): Boolean {
    if (number < 2) return false
    for (i in 2..sqrt(number).toInt()) {
        if (number % i == 0.0) return false
    }
    return true
}

fun findPrimitiveRoot(n: Double): Double {
    val phi = n - 1
    val primeFactors = findPrimeFactors(phi)

    for (candidate in n.toInt() - 999 downTo 2) {
        var isPrimitiveRoot = true
        for (factor in primeFactors) {
            if (candidate.toDouble().pow(phi / factor) % n == 1.0) {
                isPrimitiveRoot = false
                break
            }
        }
        if (isPrimitiveRoot) {
            return candidate.toDouble()
        }
    }
    return 0.0
}

fun findPrimeFactors(n: Double): List<Double> {
    val factors = mutableListOf<Double>()
    var number = n
    var divisor = 2.0
    while (number >= 2.0) {
        if (number % divisor == 0.0) {
            factors.add(divisor)
            while (number % divisor == 0.0) {
                number /= divisor
            }
        }
        divisor++
    }
    return factors
}