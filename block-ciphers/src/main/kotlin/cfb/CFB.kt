import java.io.File
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import java.util.Base64
import kotlin.system.measureTimeMillis
import java.security.SecureRandom
import javax.crypto.spec.IvParameterSpec

class CFB {

    fun generateAESKey(): SecretKey {
        val keyGen = KeyGenerator.getInstance("AES")
        keyGen.init(128)
        return keyGen.generateKey()
    }

    fun encryptCFB(inputFile: String, outputFile: String, key: SecretKey) {
        val cipher = Cipher.getInstance("AES/CFB/NoPadding")
        
        val iv = ByteArray(16)
        SecureRandom().nextBytes(iv)
        val ivSpec = IvParameterSpec(iv)

        cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec)
        
        val inputBytes = File("src/main/kotlin/input", inputFile).readBytes()

        val encryptedBytes = cipher.doFinal(inputBytes)

        File("src/main/kotlin/cfb/output", outputFile).writeBytes(iv + encryptedBytes)
    }

    fun create(): MutableList<Long> {
        val aesKey = generateAESKey()
        val aesKeyBase64 = Base64.getEncoder().encodeToString(aesKey.encoded) // just for printing purposes

        val inputFiles = listOf("file1.txt", "file2.txt", "file3.txt")

        encryptCFB("file1.txt", "file1_enc.txt", aesKey) // encrypting the first file so JIT can create the necessary classes #justJavaThings

        var timeList = mutableListOf<Long>()

        inputFiles.forEachIndexed { index, inputFile ->
            val outputFile = "file${index + 1}_enc.txt"
            val time = measureTimeMillis {
                encryptCFB(inputFile, outputFile, aesKey)
            }
            timeList.add(time)
        }

        return timeList
    }
}
