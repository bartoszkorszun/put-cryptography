import kotlin.math.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel

class AB(
    val threadName: String, 
    val n: Int, 
    val g: Int, 
    val sendChannel: Channel<Int>,    
    val receiveChannel: Channel<Int>
): Thread() {

    override fun start() {
        super.start()
        println("$name $threadName started")
    }

    override fun run() {
        val prime = generatePrime(31)
        println("$name $threadName prime number: $prime")

        val xy = riseToPowModN(g, prime, n)
        println("$g ^ $prime mod $n = $xy")

        runBlocking {
            sendChannel.send(xy)
            println("$name $threadName sent $xy")

            val yx = receiveChannel.receive()
            println("$name $threadName received $yx")
        
            val key = riseToPowModN(yx, prime, n)
            println("$name $threadName key = $key")
        }
    }    
}