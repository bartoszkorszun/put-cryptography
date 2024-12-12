import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.random.Random

class DiffieHellmanTest {

    private val prime = 23 // Example prime number
    private val primitiveRoot = 5 // Example primitive root

    @Test
    fun testKeyExchange() {
        val a = A(prime, primitiveRoot)
        val b = B(prime, primitiveRoot)

        val aThread = Thread { a.generateKey() }
        val bThread = Thread { b.generateKey() }

        aThread.start()
        bThread.start()

        aThread.join()
        bThread.join()

        assertEquals(a.sharedSecretKey, b.sharedSecretKey)
    }
}

class A(private val prime: Int, private val primitiveRoot: Int) {
    var sharedSecretKey: Int = 0
        private set

    fun generateKey() {
        val x = Random.nextInt(1, prime)
        val X = Math.pow(primitiveRoot.toDouble(), x.toDouble()).toInt() % prime

        // Simulate sending X to B and receiving Y
        val b = B(prime, primitiveRoot)
        val Y = b.generateKey()

        sharedSecretKey = Math.pow(Y.toDouble(), x.toDouble()).toInt() % prime
    }
}

class B(private val prime: Int, private val primitiveRoot: Int) {
    var sharedSecretKey: Int = 0
        private set

    fun generateKey(): Int {
        val y = Random.nextInt(1, prime)
        val Y = Math.pow(primitiveRoot.toDouble(), y.toDouble()).toInt() % prime

        // Simulate sending Y to A and receiving X
        val a = A(prime, primitiveRoot)
        val X = a.generateKey()

        sharedSecretKey = Math.pow(X.toDouble(), y.toDouble()).toInt() % prime
        return Y
    }
}