package io.github.mslxl.uitlities.graphics

import io.github.mslxl.uitlities.catch
import io.github.mslxl.uitlities.io.Resource
import org.junit.Test
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import java.net.URL
import javax.imageio.ImageIO

class TypewriterTest {


    @Test
    fun type() {
        val typewriter = Typewriter()
        var page = 1
        typewriter.apply {
            paperSupport = PaperSupportDevice {
                output { paper ->
                    catch {
                        File("/tmp/typewriter_$page.png").outputStream().use {
                            ImageIO.write(paper.data as BufferedImage, "PNG", it)
                        }
                    }
                    page++
                }
                feed {
                    BufferedImage(500, 500, BufferedImage.TYPE_4BYTE_ABGR).let {
                        Paper(it, it).apply {
                            margin.let {
                                it.top = 10
                                it.bottom = 10
                                it.left = 15
                                it.right = 15
                            }
                        }
                    }
                }
            }
            fontColor = Color.BLACK
        }


        Resource(URL("https://www.baidu.com/img/bd_logo1.png")).inputStream {
            val image = ImageIO.read(it)
            val txt = buildString {
                repeat(5 * 500) {
                    append((it + '!'.toInt()).toChar())
                    append(" ")
                }
            }
            typewriter.insertImage(image)
            typewriter.type(txt)
            typewriter.insertImage(image)
            typewriter.flush()

        }
    }
}