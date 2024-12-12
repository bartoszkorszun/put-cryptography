class B(private val n: Long, private val g: Long, private val a: A) {
    private var y: Long = (1..100).random() // Random number for B
    private var Y: Long = 0 // Y = g^y mod n
    private var sharedKey: Long = 0 // Shared secret key

    init {
        calculateY()
        exchangeKeys()
    }

    private fun calculateY() {
        Y = (g.toDouble().pow(y).toLong() % n) // Calculate Y = g^y mod n
        println("B's Y: $Y")
    }

    private fun exchangeKeys() {
        val X = a.getX() // Receive X from A
        sharedKey = (X.toDouble().pow(y).toLong() % n) // Calculate shared key k = X^y mod n
        println("B's Shared Key: $sharedKey")
    }
    
    fun getY(): Long {
        return Y
    }
}