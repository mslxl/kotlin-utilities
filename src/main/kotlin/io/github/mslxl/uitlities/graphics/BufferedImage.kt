package io.github.mslxl.uitlities.graphics

import java.awt.geom.AffineTransform
import java.awt.image.AffineTransformOp
import java.awt.image.BufferedImage


fun BufferedImage.cut(x: Int, y: Int, toX: Int, toY: Int): BufferedImage {
    val result = BufferedImage(toX - x, toY - y, type)
    for (fx in 0 until toX - x) {
        for (fy in 0 until toY - y) {
            result.setRGB(fx, fy, this.getRGB(fx + x, fy + y))
        }
    }
    return result
}

fun BufferedImage.scale(percentage: Double) = scale(this.width * percentage, this.height * percentage)

fun BufferedImage.scale(toWidth: Double, toHeight: Double): BufferedImage {
    val ato = AffineTransformOp(AffineTransform.getScaleInstance(toWidth, toHeight), null)
    val result = ato.filter(this, null)
    return result as BufferedImage
}