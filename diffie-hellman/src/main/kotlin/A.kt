class A(private val g: Int, private val n: Int) {
    private val x: Int = (1..100).random() // Random number x
    val X: Int = modPow(g, x, n) // Calculate X = g^x mod n
    var sharedKey: Int? = null

    fun receiveY(Y: Int) {
        sharedKey = modPow(Y, x, n) // Calculate shared key k = Y^x mod n
    }

    private fun modPow(base: Int, exponent: Int, modulus: Int): Int {
        var result = 1
        var b = base % modulus
        var exp = exponent

        while (exp > 0) {
            if (exp % 2 == 1) {
                result = (result * b) % modulus
            }
            b = (b * b) % modulus
            exp /= 2
        }
        return result
    }
}