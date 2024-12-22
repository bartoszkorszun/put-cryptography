import java.io.File
import java.security.MessageDigest
import kotlin.random.Random
import kotlin.system.measureNanoTime

fun getFirst12Bits(hash: String): String {
    return hash.take(3)
}

fun checkCollisions(algorithm: String) {
    val seenHashes = mutableSetOf<String>()
    var collisionFound = false
    val maxAttempts = 1_000
    var attempts = 0

    while (!collisionFound && attempts < maxAttempts) {
        val randomInput = Random.nextBytes(16).joinToString("") { "%02x".format(it) }
        val hash = generateHash(randomInput, algorithm)
        val prefix = getFirst12Bits(hash)

        if (seenHashes.contains(prefix)) {
            println("Collision found after $attempts attempts")
            collisionFound = true
        } else {
            seenHashes.add(prefix)
        }

        attempts++
    }

    if (!collisionFound) {
        println("Nie znaleziono kolizji po $maxAttempts próbach.")
    }
}

fun space(algorithm: String): String {
    val spaces = " ".repeat(9 - algorithm.length)
    return if (spaces.length > 0) spaces else " "
}

fun generateHash(input: String, algorithm: String): String {
    val bytes = MessageDigest.getInstance(algorithm).digest(input.toByteArray())
    return bytes.joinToString("") { "%02x".format(it) }
}

fun encryptAll() {
    val input = File("src/main/kotlin/input.txt").readText().split("\n\n")
    val output = File("src/main/kotlin/output.txt").bufferedWriter()

    val algorithms = listOf("MD5", "SHA-1", "SHA-256", "SHA-384", "SHA-512", "SHA3-256", "SHA3-512")
    val unused = generateHash(input[0], algorithms[0]) // Just fot JIT...

    for (i in input.indices) {
        output.write("Input size: ${input[i].length}\n")
        val timeList = mutableListOf<Double>()
        algorithms.forEach { algorithm ->
            val time = measureNanoTime {
                val hash = generateHash(input[i], algorithm)
                output.write("$algorithm:${space(algorithm)}$hash\n")
            }
            timeList.add(time / 1_000_000.0)
        }
        output.write("-------------------------\n")

        for (j in algorithms.indices) {
            output.write("${algorithms[j]}:${space(algorithms[j])}%.3f ms\n".format(timeList[j]))
        }
        output.write("===========================================================================\n")
    }
    output.close()
}