fun main() {
    val (p, q) = generatePrimes(31)
    println("p   = $p\nq   = $q")

    val n = generateN(p, q)
    println("n   = $n")

    val (e, d) = generateED(p, q)
    println("e   = $e\nd   = $d")

    val message = 6519991300L
    println("Message: $message")

    val encrypted = encryptDecrypt(message, e, n)
    println("Encrypted: $encrypted")

    val decrypted = encryptDecrypt(encrypted, d, n)
    println("Decrypted: $decrypted")
}