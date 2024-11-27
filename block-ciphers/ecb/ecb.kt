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
    val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding") // PKCS5Padding ensures that the input is padded to a multiple of 16 bytes
    cipher.init(Cipher.ENCRYPT_MODE, key)

    val inputBytes = File("input", inputFile).readBytes()
    val encryptedBytes = cipher.doFinal(inputBytes)

    File("output", outputFile).writeBytes(encryptedBytes)
}

fun main() {
    println("Generating AES key...\n")
    val aesKey = generateAESKey()
    val aesKeyBase64 = Base64.getEncoder().encodeToString(aesKey.encoded) // just for printing purposes
    println("AES key generated:\n$aesKeyBase64 \n")

    val inputFiles = listOf("file1.txt", "file2.txt", "file3.txt")

    encryptECB("file1.txt", "file1_enc.txt", aesKey) // encrypting the first file so JIT can create the necessary classes #justJavaThings

    inputFiles.forEachIndexed { index, inputFile ->
        val outputFile = "file${index + 1}_enc.txt"
        println("Encrypting $inputFile to $outputFile...")
        val time = measureTimeMillis {
            encryptECB(inputFile, outputFile, aesKey)
        }
        println("Encryption time: $time ms\n")
    }

    println("Encryption finished.")
}