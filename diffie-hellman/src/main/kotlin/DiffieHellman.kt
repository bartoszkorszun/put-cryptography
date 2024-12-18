import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel

fun main() {
    val n = generatePrime(31) 
    val g = findPrimitiveRoot(n)

    println("Prime number: $n \nPrimitive root: $g")

    val channel1to2 = Channel<Int>(1)
    val channel2to1 = Channel<Int>(2)

    val a = AB("A", n, g, channel1to2, channel2to1)
    val b = AB("B", n, g, channel2to1, channel1to2)

    a.start()
    b.start()

    a.join()
    b.join()
}