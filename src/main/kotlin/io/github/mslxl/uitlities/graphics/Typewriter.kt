package io.github.mslxl.uitlities.graphics

import io.github.mslxl.uitlities.log.log
import io.github.mslxl.uitlities.logic.isTrue
import java.awt.Color
import java.awt.Font
import java.awt.image.BufferedImage


class Typewriter {

    private lateinit var paper: Paper
    val space = Space()
    var font = Font.getFont(Font.MONOSPACED)
    var fontColor = Color.BLACK!!
    var paperSupport: PaperSupportDevice = PaperSupportDevice {}
    private var firstPaper = true
    private var firstLine = true
    private val position = Position()
    private var needNewLine = false
    private var lastCharHeight = 0
    private fun nextPaper(out: Boolean = true) {
        out.isTrue {
            paperSupport.output.invoke(paper)
        }
        paper = paperSupport.feed.invoke()
        restPos()
        firstLine = true
    }

    @JvmOverloads
    fun type(txt: String, size: Float = -1F) {
        txt.chars().forEach { typeChar(it.toChar(), size) }
    }

    private fun moveDown(height: Int) {
        resetPosX()
        position.y += (height + space.lineSpacing)
    }

    private fun resetPosX() {
        position.x = paper.margin.left
    }

    private fun resetPosY() {
        position.y = paper.margin.top
    }

    private fun restPos() {
        resetPosX()
        resetPosY()
    }

    private fun movePosition(width: Int, height: Int) {
        if (position.x >= paper.width - paper.margin.right || needNewLine) {
            // Next line?
            moveDown(height)
            needNewLine = false
        }

        if (position.y >= paper.height - paper.margin.bottom) {
            // Next page ?
            nextPaper()
        }

        if (position.x == 0 && position.y == 0) {
            // Skip  margin
            resetPosX()
            position.y = paper.margin.top + height
        }
        lastCharHeight = height
    }

    private fun checkFirstPaper() {
        firstPaper.isTrue {
            nextPaper(false)
            firstPaper = false
        }
    }

    private fun checkFirstLine(fontHeight: Int) {
        firstLine.isTrue {
            position.x += fontHeight
            firstLine = false
        }
    }

    @JvmOverloads
    fun typeChar(char: Char, size: Float = -1F) {
        checkFirstPaper()

        paper.graphics.let {

            it.font = if (size < 0) this@Typewriter.font else this@Typewriter.font.deriveFont(size)
            it.color = fontColor
            val metrics = it.fontMetrics
            val charWidth = metrics.charWidth(char)

            checkFirstLine(metrics.height)
            movePosition(charWidth, metrics.height)

            it.drawString(char.toString(), position.x, position.y)
            char.log("Type ${position.x} X ${position.y}")
            // Next char
            position.x += (charWidth + space.wordSpacing)
        }
    }

    @JvmOverloads
    fun insertImage(image: BufferedImage, zoom: Boolean = true) {
        checkFirstPaper()
        var targetWidth = image.width
        var targetHeight = image.height
        var targetImage = image
        if (zoom) {
            val paperHeight = paper.height - position.y - paper.margin.bottom
            val paperWidth = paper.width - paper.margin.left - paper.margin.right

            if (paperHeight < targetHeight || paperWidth < targetWidth) {
                val heightDifference = targetHeight - paperHeight
                val widthDifference = targetWidth - paperWidth
                val proportion = if (heightDifference > widthDifference) {
                    // According height
                    paperHeight.toFloat() / targetHeight
                } else {
                    // According width
                    paperWidth.toFloat() / targetWidth
                }
                targetHeight = (targetHeight * proportion).toInt()
                targetWidth = (targetWidth * proportion).toInt()
                targetImage = targetImage.scale(targetWidth, targetHeight)
            }
        }
        moveDown(lastCharHeight)
        paper.graphics.drawImage(targetImage, position.x, position.y, null)
        moveDown(targetHeight)
        needNewLine = true
    }

    /**
     * 结束当前页并输出
     */
    fun flush() {
        nextPaper(true)
    }

    class Space {
        var wordSpacing = 0
        var lineSpacing = 0
    }

    private class Position {
        var x = 0
        var y = 0
    }
}
