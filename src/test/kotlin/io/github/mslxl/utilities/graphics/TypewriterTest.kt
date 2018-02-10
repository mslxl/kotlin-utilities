package io.github.mslxl.utilities.graphics

import io.github.mslxl.utilities.io.Resource
import io.github.mslxl.utilities.logic.whether
import io.github.mslxl.utilities.num.Counter
import io.github.mslxl.utilities.string.times
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
                File(dir, count.inc().toString() + ".png").apply {
                    if (exists()) delete()
                }.outputStream().use {
                    ImageIO.write(paper.bufferedImage, "PNG", it)
                }
            }
        }
        val bilibiliHeader = Resource(URL("http://i0.hdslb.com/bfs/archive/63cb17d6ffaa7357231cea4634c3a2cf10782f49.png")).inputStream { ImageIO.read(it) }
        val bilibiliIcon = Resource(URL("http://i0.hdslb.com/bfs/archive/26233c9236c4c20f6862c4d1fd56a023c15a1b57.png")).inputStream { ImageIO.read(it) }

        val typewriter = Typewriter(device)
        typewriter.font = Font(Font.MONOSPACED, Font.PLAIN, 12)
        typewriter.println("TOP")
        repeat(30) {
            typewriter.insertImage(bilibiliHeader)
        }
        typewriter.print("hello,world\n" * 25, 10F)
        typewriter.print("1" * 500, 10F)
        typewriter.print("#" * 500, 10F)
        typewriter.println("哔哩哔哩 (゜-゜)つロ 干杯~-bilibili " * 500, 10F)
        typewriter.insertImage(bilibiliHeader)
        typewriter.insertImage(bilibiliIcon)
        typewriter.print("#" * 500, 10F)
        repeat(90) {
            typewriter.fillLine("Title $it ", '·', " P$it")
        }

        typewriter.fillLine("哔哩哔哩 (゜-゜)つロ 干杯 ")
        typewriter.flush()


    }
}