import java.io.File
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import java.util.Base64
import kotlin.system.measureTimeMillis
import java.security.SecureRandom

class CBC {

    fun generateAESKey(): SecretKey {
        val keyGen = KeyGenerator.getInstance("AES")
        keyGen.init(128)
        return keyGen.generateKey()
    }

    fun encryptCBC(inputFile: String, outputFile: String, key: SecretKey) {
        val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding") // PKCS5Padding ensures that the input is padded to a multiple of 16 bytes
        
        val iv = ByteArray(16)
        SecureRandom().nextBytes(iv)
        
        val inputBytes = File("input", inputFile).readBytes()
        val encryptedBytes = mutableListOf<Byte>()

        var previousBlock = iv

        for (i in inputBytes.indices step 16) {
            val block = inputBytes.copyOfRange(i, minOf(i + 16, inputBytes.size))
            
            val xoredBlock = block.zip(previousBlock) { b, prev -> 
                b.toInt() xor prev.toInt()
            }.map { 
                it.toByte() 
            }.toByteArray()

            cipher.init(Cipher.ENCRYPT_MODE, key)

            val encryptedBlock = cipher.doFinal(xoredBlock)

            encryptedBytes.addAll(encryptedBlock.toList())
            previousBlock = encryptedBlock
        }

        File("output", outputFile).writeBytes(iv + encryptedBytes.toByteArray())
    }

    fun create(): MutableList<Long> {
        val aesKey = generateAESKey()
        val aesKeyBase64 = Base64.getEncoder().encodeToString(aesKey.encoded) // just for printing purposes

        val inputFiles = listOf("file1.txt", "file2.txt", "file3.txt")

        encryptCBC("file1.txt", "file1_enc.txt", aesKey) // encrypting the first file so JIT can create the necessary classes #justJavaThings

        var timeList = mutableListOf<Long>()

        inputFiles.forEachIndexed { index, inputFile ->
            val outputFile = "file${index + 1}_enc.txt"
            val time = measureTimeMillis {
                encryptCBC(inputFile, outputFile, aesKey)
            }
            timeList.add(time)
        }

        return timeList
    }
}
