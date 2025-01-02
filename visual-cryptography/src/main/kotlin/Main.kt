fun main() {
    val resFolder = "res"
    val inputImg = "$resFolder/input.png"
    val share1Img = "$resFolder/share1.png"
    val share2Img = "$resFolder/share2.png"
    val outputImg = "$resFolder/output.png"

    createShares(inputImg, share1Img, share2Img)

    overlayShares(share1Img, share2Img, outputImg)
}