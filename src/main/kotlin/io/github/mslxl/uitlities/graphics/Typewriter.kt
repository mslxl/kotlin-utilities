package io.github.mslxl.uitlities.graphics

import io.github.mslxl.uitlities.log.log
import io.github.mslxl.uitlities.logic.isTrue
import java.awt.*


class Typewriter {

    private lateinit var paper: Paper
    private var graphics: Graphics? = null
        set(value) {
            if (value == null) throw NullPointerException()
            (value as? Graphics2D)?.run {
                setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                        RenderingHints.VALUE_TEXT_ANTIALIAS_ON)
                setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_DEFAULT)
            }
            value.color = this@Typewriter.fontColor
            field = value
        }
    val margin = Margin()
    var font = Font.getFont(Font.SANS_SERIF)
    var fontColor = Color.BLACK
        set(value) {
            field = value
            graphics?.color = value
        }
    private var first = true
    private val position = Position()


    private fun nextPaper(out: Boolean = true) {
        out.isTrue {
            output.invoke(paper)
        }
        paper = feed.invoke()
        graphics = paper.graphics
        position.reset()
    }

    fun type(txt: String, size: Float = -1F) {
        txt.chars().forEach { typeChar(it.toChar(), size) }
    }

    fun typeChar(char: Char, size: Float = -1F) {
        first.isTrue {
            nextPaper(false)
            first = false
        }
        graphics!!.let {

            it.font = if (size < 0) this@Typewriter.font else this@Typewriter.font.deriveFont(size)

            val metrics = it.fontMetrics
            val charWidth = metrics.charWidth(char)
            if (position.x >= paper.width - margin.right) {
                // Next line?
                position.x = margin.left
                position.y += metrics.height
            }

            if (position.y >= paper.height - margin.bottom) {
                // Next page ?
                position.reset()
                nextPaper()
            }

            if (position.x == 0 && position.y == 0) {
                // Skip  margin
                position.x = margin.left
                position.y = margin.top + metrics.height
            }

            it.drawString(char.toString(), position.x, position.y)
            char.log("Type")
            // Next char
            position.x += charWidth
        }
    }

    fun flush() {
        nextPaper(true)
    }

    var feed: () -> Paper = {
        error("No feed")
    }
    var output: (Paper) -> Unit = {}

    class Margin {
        var top = 0
        var bottom = 0
        var left = 0
        var right = 0
    }

    private class Position {
        var x = 0
        var y = 0

        fun reset() {
            x = 0
            y = 0
        }
    }
}
