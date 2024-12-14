import kotlin.math.*

class AB(name: String, n: Double, g: Double): Thread() {

    private var num: Double
    private var prime: Double

    override fun start() {
        println("Thread $name started")
    }

    init {
        prime = generatePrime(31)
        num = g.pow(prime) % n
        println("num $num")
    }

    fun calculateK(xy: Double, n: Double): Double {
        println("calc K ${xy.pow(prime)}")
        return xy.pow(prime) % n
    }

    fun getNum(): Double {
        return num
    }
}