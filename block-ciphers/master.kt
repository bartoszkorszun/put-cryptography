import java.io.File

fun executePrecompiledJar(algorithm: String) {
    val currentDir = File(".").absolutePath  // Current working directory

    val jarDir = File("$currentDir/$algorithm")

    val jarFile = File(jarDir, "${algorithm}.jar")
    if (!jarFile.exists()) {
        println("Error: JAR file not found: ${jarFile.absolutePath}")
        return
    }

    val command = "java -jar $jarFile"
    println("Executing command: $command")

    val process = ProcessBuilder(command.split(" "))
        .directory(jarDir)
        .inheritIO()
        .start()

    process.waitFor()
}


fun main() {
    val algorithms = listOf("cbc", "cfb", "ctr", "ecb", "ofb")
    println("Select an algorithm:")
    algorithms.forEachIndexed { index, name -> println("${index + 1}. $name") }

    val choice = readLine()?.toIntOrNull()
    if (choice == null || choice !in 1..algorithms.size) {
        println("Invalid choice. Exiting.")
        return
    }

    val selectedAlgorithm = algorithms[choice - 1]
    executePrecompiledJar(selectedAlgorithm)
}
