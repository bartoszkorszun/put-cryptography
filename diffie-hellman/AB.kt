import kotlin.math.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel

class AB(
    private name: String, 
    private n: Int, 
    private g: Int, 
    private sendChanned: Channel<Int>,    
    private receiveChannel: Channel<Int>
): Thread() {

    override fun start() {
        println("Thread $name started")
    }

    override fun run() {
        val prime = generatePrime(31)
        println("Prime number: $prime")
    }    
}