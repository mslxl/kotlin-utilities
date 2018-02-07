package io.github.mslxl.uitlities.graphics

import io.github.mslxl.uitlities.io.Resource
import io.github.mslxl.uitlities.logic.whether
import io.github.mslxl.uitlities.num.Counter
import io.github.mslxl.uitlities.string.times
import org.junit.Test
import java.awt.Font
import java.awt.image.BufferedImage
import java.io.File
import java.net.URL
import javax.imageio.ImageIO

class TypewriterTest {


    @Test
    fun type() {
        val device = object : PaperSupportDevice<BufferedImagePaper> {
            override fun feed(): BufferedImagePaper {
                return BufferedImagePaper(BufferedImage(2480, 3580, BufferedImage.TYPE_4BYTE_ABGR)).apply {
                    margin.top = 40
                    margin.bottom = 100
                    margin.right = 30
                    margin.left = 30
                }
            }

            val dir = File("out/tmp").whether { exists() }.isFalse { mkdirs() }.source
            val count = Counter(0)
            override fun output(paper: BufferedImagePaper) {
                File(dir, count.inc().toString() + "-${paper.graphics.hashCode()}.png").apply {
                    if (exists()) delete()
                }.outputStream().use {
                    ImageIO.write(paper.bufferedImage, "PNG", it)
                }
            }
        }
        Resource(URL("https://www.baidu.com/img/bd_logo1.png")).inputStream { imageStream ->
            val image = ImageIO.read(imageStream).scale(0.5F)
            val typewriter = Typewriter(device)
            typewriter.font = Font(Font.MONOSPACED, Font.PLAIN, 12)
            typewriter.insertImage(image)
            typewriter.println("hello,world" * 100 * 50, 10F)
            typewriter.insertImage(image)
            typewriter.println("hello,world" * 100 * 50, 10F)
            typewriter.insertImage(image)
            typewriter.flush()
        }

    }
}