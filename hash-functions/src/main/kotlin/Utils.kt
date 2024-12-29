import java.io.File
import java.security.MessageDigest
import kotlin.random.Random
import kotlin.system.measureNanoTime

fun generateHashBytes(input: ByteArray, algorithm: String): ByteArray {
    return MessageDigest.getInstance(algorithm).digest(input)
}

fun calculateBitDifference(hash1: ByteArray, hash2: ByteArray): Int {
    var differences = 0
    for (i in hash1.indices) {
        val xor = hash1[i].toInt() xor hash2[i].toInt()
        differences += Integer.bitCount(xor)
    }
    return differences
}

fun testSAC(input: String, algorithm: String) {
    val originalBytes = input.toByteArray()
    val originalHash = generateHashBytes(originalBytes, algorithm)

    println("Testing SAC for $algorithm")
    println("Input length: ${originalBytes.size} bytes")

    val bitDifferences = mutableListOf<Int>()

    for (bitPosition in 0 until originalBytes.size * 8) {
        val modifiedBytes = originalBytes.copyOf()

        val byteIndex = bitPosition / 8
        val bitIndex = bitPosition % 8
        modifiedBytes[byteIndex] = (modifiedBytes[byteIndex].toInt() xor (1 shl bitIndex)).toByte()

        val modifiedHash = generateHashBytes(modifiedBytes, algorithm)

        val bitDifference = calculateBitDifference(originalHash, modifiedHash)
        bitDifferences.add(bitDifference)
    }

    val averageDifference = bitDifferences.average()
    println("Average bit difference: $averageDifference")
    println("Expected average for SAC: ${originalHash.size * 8 / 0.5}")
}

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