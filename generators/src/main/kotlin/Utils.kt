import java.security.SecureRandom
import java.math.BigInteger

fun generatePrime(bits: Int): Int {
    val secureRandom = SecureRandom()
    while (true) {
        val candidate = secureRandom.nextInt((1 shl bits) - 1) + (1 shl (bits - 1))
        if (candidate % 4 == 3) {
            return candidate
        }
    }
}

fun gcd(a: Long, b: Long): Long {
    var num1 = a
    var num2 = b
    while (num2 != 0L) {
        val temp = num2
        num2 = num1 % num2
        num1 = temp
    }
    return num1
}


fun generateX(n: Long): Long {
    val secureRandom = SecureRandom()
    while(true) {
        val x = secureRandom.nextLong(1, n-1)
        if (gcd(x, n) == 1L) {
            return x
        }
    }
}

fun generateSeq(length: Int): String {
    val p = generatePrime(31)
    val q = generatePrime(31)
    val n = p.toLong() * q.toLong()
    val x = generateX(n)

    val x0 = BigInteger.valueOf(x)
    .modPow(
        BigInteger.valueOf(x), 
        BigInteger.valueOf(n)
    )
    .toLong()

    val bitSeq = StringBuilder()
    var xi = x0
    for (i in 0 until length) {
        xi = BigInteger.valueOf(xi)
        .modPow(
            BigInteger.valueOf(xi), 
            BigInteger.valueOf(n)
        ).toLong()

        bitSeq.append((xi % 2).toString())
    }
    return bitSeq.toString()
}