package io.github.mslxl.utilities.graphics

import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.RenderingHints
import java.awt.image.BufferedImage

open class Paper(val graphics: Graphics, val width: Int, val height: Int) {
    var margin = Margin()

    class Margin {
        var top = 0
        var bottom = 0
        var left = 0
        var right = 0
    }

    init {
        this.graphics.apply {
            (this as? Graphics2D)?.run {
                setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON)
                setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_DEFAULT)
            }
        }
    }

}

class BufferedImagePaper(val bufferedImage: BufferedImage) : Paper(bufferedImage.graphics, bufferedImage.width, bufferedImage.height)