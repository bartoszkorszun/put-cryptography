fun main() {
    val n = 23 // A prime number
    val g = 5  // A primitive root modulo n

    val a = A(n, g)
    val b = B(n, g)

    // Start threads for A and B
    val threadA = Thread { a.start() }
    val threadB = Thread { b.start() }

    threadA.start()
    threadB.start()

    threadA.join()
    threadB.join()

    // Output the shared secret keys
    println("Shared secret key from A: ${a.sharedSecretKey}")
    println("Shared secret key from B: ${b.sharedSecretKey}")
}

class A(private val n: Int, private val g: Int) {
    var sharedSecretKey: Int = 0
        private set

    fun start() {
        val x = (1..n).random() // Generate random number x
        val X = modPow(g, x, n) // Calculate X = g^x mod n

        // Simulate sending X to B and receiving Y
        val Y = B(n, g).calculateY() // In a real scenario, this would be received from B
        sharedSecretKey = modPow(Y, x, n) // Calculate shared secret key k = Y^x mod n
    }

    private fun modPow(base: Int, exp: Int, mod: Int): Int {
        var result = 1
        var b = base % mod
        var e = exp

        while (e > 0) {
            if (e % 2 == 1) {
                result = (result * b) % mod
            }
            b = (b * b) % mod
            e /= 2
        }
        return result
    }
}

class B(private val n: Int, private val g: Int) {
    var sharedSecretKey: Int = 0
        private set

    fun start() {
        val y = (1..n).random() // Generate random number y
        val Y = modPow(g, y, n) // Calculate Y = g^y mod n

        // Simulate sending Y to A and receiving X
        val X = A(n, g).calculateX() // In a real scenario, this would be received from A
        sharedSecretKey = modPow(X, y, n) // Calculate shared secret key k = X^y mod n
    }

    fun calculateY(): Int {
        return modPow(g, (1..n).random(), n) // Simulate sending Y
    }

    fun calculateX(): Int {
        return modPow(g, (1..n).random(), n) // Simulate sending X
    }

    private fun modPow(base: Int, exp: Int, mod: Int): Int {
        var result = 1
        var b = base % mod
        var e = exp

        while (e > 0) {
            if (e % 2 == 1) {
                result = (result * b) % mod
            }
            b = (b * b) % mod
            e /= 2
        }
        return result
    }
}