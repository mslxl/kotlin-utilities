package io.github.mslxl.uitlities.graphics

import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.RenderingHints
import java.awt.image.BufferedImage

class Paper {
    val graphics: Graphics
    val width: Int
    val height: Int
    val data: Any?
    var margin = Margin()

    constructor(bufferedImage: BufferedImage, data: Any? = null) : this(bufferedImage.graphics, bufferedImage.width, bufferedImage.height, data)
    constructor(graphics: Graphics, width: Int, height: Int, data: Any? = null) {
        this.graphics = graphics
        this.width = width
        this.height = height
        this.data = data
        this.graphics.apply {
            (this as? Graphics2D)?.run {
                setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON)
                setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_DEFAULT)
            }
        }
    }

    class Margin {
        var top = 0
        var bottom = 0
        var left = 0
        var right = 0
    }
}