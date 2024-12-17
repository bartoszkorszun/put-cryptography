import kotlin.math.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel

class AB(
    name: String, 
    n: Int, 
    g: Int, 
    sendChanned: Channel<Int>,    
    receiveChannel: Channel<Int>
): Thread() {

    override fun start() {
        println("$name started")
        super.start()
    }

    override fun run() {
        val prime = generatePrime(31)
        println("Prime number: $prime")
    }    
}