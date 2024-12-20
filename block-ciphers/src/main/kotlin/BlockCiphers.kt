fun main() {
    val ecb = ECB()
    val cbc = CBC()
    val cfb = CFB()
    val ofb = OFB()
    val ctr = CTR()

    val timeList = listOf(
        ecb.create(),
        cbc.create(),
        cfb.create(),
        ofb.create(),
        ctr.create()
    )

    val nameList = listOf(
        "ECB",
        "CBC",
        "CFB",
        "OFB",
        "CTR"
    )

    println("     0,5MB     1MB     5MB")
    for (time in timeList) {
        print("${nameList[timeList.indexOf(time)]}    ")
        for (i in 0..2) {
            print("${time[i]}")
            if (time[i] < 10) {
                print("       ")
            } else {
                print("      ")
            }
        }
        println()
    }
}
