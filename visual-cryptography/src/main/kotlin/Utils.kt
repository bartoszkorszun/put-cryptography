import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import kotlin.random.Random

fun createShares(imagePath: String, share1Path: String, share2Path: String) {
    val inputImg = ImageIO.read(File(imagePath))
    val width = inputImg.width
    val height = inputImg.height

    val share1Img = BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY)
    val share2Img = BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY)

    for (x in 0 until width) {
        for (y in 0 until height) {
            val color = Color(inputImg.getRGB(x, y)).red
            val pixelValue = if (color < 128) 0 else 1

            if (pixelValue == 0) {
                if (Random.nextBoolean()) {
                    share1Img.setRGB(x, y, Color.WHITE.rgb)
                    share2Img.setRGB(x, y, Color.BLACK.rgb)
                } else {
                    share1Img.setRGB(x, y, Color.BLACK.rgb)
                    share2Img.setRGB(x, y, Color.WHITE.rgb)
                }
            } else {
                if (Random.nextBoolean()) {
                    share1Img.setRGB(x, y, Color.WHITE.rgb)
                    share2Img.setRGB(x, y, Color.WHITE.rgb)
                } else {
                    share1Img.setRGB(x, y, Color.BLACK.rgb)
                    share2Img.setRGB(x, y, Color.BLACK.rgb)
                }
            }
        }
    }

    ImageIO.write(share1Img, "png", File(share1Path))
    ImageIO.write(share2Img, "png", File(share2Path))
}

fun overlayShares(share1Path: String, share2Path: String, outputPath: String) {
    val share1Img = ImageIO.read(File(share1Path))
    val share2Img = ImageIO.read(File(share2Path))
    
    val width = share1Img.width
    val height = share1Img.height

    val outputImg = BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY)

    for (x in 0 until width) {
        for (y in 0 until height) {
            val pixel1 = Color(share1Img.getRGB(x, y)).red
            val pixel2 = Color(share2Img.getRGB(x, y)).red

            val pixelValue = if ((pixel1 <= 128 && pixel2 > 128) || (pixel1 > 128 && pixel2 <= 128)) Color.BLACK.rgb else Color.WHITE.rgb

            outputImg.setRGB(x, y, pixelValue)
        }
    }

    ImageIO.write(outputImg, "png", File(outputPath))
}