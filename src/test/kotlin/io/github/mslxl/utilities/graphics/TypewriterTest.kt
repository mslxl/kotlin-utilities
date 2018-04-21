package io.github.mslxl.utilities.graphics

import io.github.mslxl.utilities.logic.whether
import io.github.mslxl.utilities.num.Counter
import io.github.mslxl.utilities.string.times
import org.junit.Test
import java.awt.Font
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

class TypewriterTest {


    @Test
    fun type() {
        val device = object : PaperSupportDevice<BufferedImagePaper> {
            override fun feed(): BufferedImagePaper {
                return BufferedImagePaper(BufferedImage(1240, 3580, BufferedImage.TYPE_4BYTE_ABGR)).apply {
                    margin.top = 30
                    margin.bottom = 30
                    margin.right = 30
                    margin.left = 30
                }
            }

            val dir = File("out/tmp").whether { exists() }.isFalse { mkdirs() }.source
            val count = Counter(0)
            override fun output(paper: BufferedImagePaper) {
                File(dir, count.inc().toString() + ".png").apply {
                    if (exists()) delete()
                }.outputStream().use {
                    ImageIO.write(paper.bufferedImage, "PNG", it)
                }
            }
        }
        val typewriter = Typewriter(device)
        typewriter.font = Font(Font.MONOSPACED, Font.PLAIN, 12)
        typewriter.println("TOP")



        repeat(400) {
            typewriter.println((it.toString() + " ") * 20, 10F)
        }
    }
}