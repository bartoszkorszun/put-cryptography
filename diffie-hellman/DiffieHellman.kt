import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel

fun main() {
    val n = generatePrime(31) 
    val g = findPrimitiveRoot(n)

    println("Prime number: $n \nPrimitive root: $g")

    runBlocking {
        val channel1to2 = Channel<Int>()
        val channel2to1 = Channel<Int>()

        val a = AB("A", n, g, channel1to2, channel2to1)
        val b = AB("B", n, g, channel2to1, channel1to2)

        a.start()
        b.start()

        a.join()
        b.join()
    }
}