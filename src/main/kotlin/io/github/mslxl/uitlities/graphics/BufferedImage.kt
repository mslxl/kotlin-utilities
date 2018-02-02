package io.github.mslxl.uitlities.graphics

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

fun BufferedImage.scale(percentage: Float) = scale((this.width * percentage).toInt(), (this.height * percentage).toInt())

fun BufferedImage.scale(targetWidth: Int, targetHeight: Int): BufferedImage {
    val result = BufferedImage(targetWidth, targetHeight, type)
    result.graphics.drawImage(getScaledInstance(targetWidth, targetHeight, java.awt.Image.SCALE_SMOOTH), 0, 0, null)
    return result
}