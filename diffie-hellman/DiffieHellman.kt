fun main() {
    val n = generatePrime(31) 
    val g = findPrimitiveRoot(n)

    println("Prime number: $n \nPrimitive root: $g")

    val a = AB("A", n, g)
    val b = AB("B", n, g)

    a.start()
    b.start()

    println("X: ${a.getNum()} \nY: ${b.getNum()}")

    a.join()
    b.join()

    println("A: k = ${a.calculateK(b.getNum(), n)}")
    println("B: k = ${b.calculateK(a.getNum(), n)}")
}