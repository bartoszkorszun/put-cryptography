fun main() {
    encryptAll()
    checkCollisions("SHA3-512")

    val input = "This is a test input for SAC analysis."
    println()
    testSAC(input, "SHA3-512")
}

