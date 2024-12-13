import java.math.BigInteger
import java.security.SecureRandom
import java.util.*

fun main() {
    val n = generatePrime(128)
    val g = findPrimitiveRoot(n)

    println("Prime number: $n \nPrimitive root: $g")

    
}

fun generatePrime(bits: Int): BigInteger {
    val random = SecureRandom() 
    return BigInteger.probablePrime(bits, random)
}

fun findPrimitiveRoot(n: BigInteger): BigInteger {
    val primeSet = mutableSetOf<BigInteger>()
    val phi = n - BigInteger.ONE

    findPrimeFactors(primeSet, phi)

    for (i in 8999999..9999999) {
        var isPrimitiveRoot = false
        val ni = n.subtract(BigInteger(i.toString()))
        for (prime in primeSet) {
            if (power(ni, phi / prime, n) == BigInteger.ONE) {
                isPrimitiveRoot = true
                break
            }
        }
        if (!isPrimitiveRoot) {
            return ni
        }
    }

    return BigInteger.ZERO
}

fun findPrimeFactors(primeSet: MutableSet<BigInteger>, n: BigInteger) {
    var num = n
    while (num % BigInteger.TWO == BigInteger.ZERO) {
        primeSet.add(BigInteger.TWO)
        num /= BigInteger.TWO
    }

    for (i in 3 until num.sqrt().toInt() step 2) {
        while (num % BigInteger(i.toString()) == BigInteger.ZERO) {
            primeSet.add(BigInteger(i.toString()))
            num /= BigInteger(i.toString())
        }
    }

    if (num > BigInteger.TWO) {
        primeSet.add(num)
    }
}

fun power(a: BigInteger, b: BigInteger, mod: BigInteger): BigInteger {
    var result = BigInteger.ONE
    var x = a % mod

    var y = b
    while (y > BigInteger.ZERO) {
        if (y % BigInteger.TWO == BigInteger.ONE) {
            result = (result * x) % mod
        }
        x = (x * x) % mod
        y /= BigInteger.TWO
    }

    return result
}