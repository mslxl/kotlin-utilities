package io.github.mslxl.uitlities.graphics

import io.github.mslxl.uitlities.catch
import org.junit.Test
import java.awt.Color
import java.io.File
import javax.imageio.ImageIO

class TypewriterTest {


    @Test
    fun type() {
        val typewriter = Typewriter()
        typewriter.feed = {
            Paper(500, 500, Paper.TYPE_4BYTE_ABGR)
        }
        var page = 1
        typewriter.apply {
            output = { paper ->
                catch {
                    File("/tmp/typewriter_$page.png").outputStream().use {
                        ImageIO.write(paper, "PNG", it)
                    }
                }
                page++
            }
            margin.apply {
                top = 10
                bottom = 10
                left = 15
                right = 15
            }
            fontColor = Color.BLACK
        }


        val txt = buildString {
            repeat(5 * 1000) {
                append(it.toChar())
                append(" ")
            }
        }

        typewriter.type(txt)
        typewriter.flush()
    }
}