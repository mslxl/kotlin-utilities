package io.github.mslxl.utilities.graphics

class Dpi(val dpi: Int) {
    fun pixel2mm(pixel: Float) = pixel / dpi * 25.4
    fun mm2pixel(mm: Float) = mm / 25.4 * dpi
}