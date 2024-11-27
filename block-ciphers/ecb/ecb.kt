import java.io.File
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import java.util.Base64
import kotlin.system.measureTimeMillis

fun generateAESKey(): SecretKey {
    val keyGen = KeyGenerator.getInstance("AES")
    keyGen.init(128)
    return keyGen.generateKey()
}

fun encryptECB(inputFile: String, outputFile: String, key: SecretKey) {
    val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
    cipher.init(Cipher.ENCRYPT_MODE, key)

    val inputBytes = File("input", inputFile).readBytes()
    val encryptedBytes = cipher.doFinal(inputBytes)

    File("output", outputFile).writeBytes(encryptedBytes)
}

fun main() {
    println("Generating AES key...")
    val aesKey = generateAESKey()
    val aesKeyBase64 = Base64.getEncoder().encodeToString(aesKey.encoded) // just for printing purposes
    println("AES key generated:\n$aesKeyBase64")

    val inputFiles = listOf("file1.txt", "file2.txt", "file3.txt")

    inputFiles.forEachIndexed { index, inputFile ->
        val outputFile = "file${index + 1}_enc.txt"
        println("Encrypting $inputFile to $outputFile...")
        val time = measureTimeMillis {
            encryptECB(inputFile, outputFile, aesKey)
        }
        println("Encryption time: $time ms")
    }

    println("Encryption finished.")
}