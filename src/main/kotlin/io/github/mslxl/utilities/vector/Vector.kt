package io.github.mslxl.utilities.vector

import kotlin.math.acos
import kotlin.math.sqrt

@Suppress("FunctionName", "NonAsciiCharacters")
open class Vector(val x: Double, val y: Double) {
    companion object {
        val unitX = Vector(1.0, 0.0)
        val unitY = Vector(0.0, 1.0)
    }

    operator fun plus(v: Vector) = Vector(x + v.x, y + v.y)

    val norm = sqrt(x * x + y * y)

    operator fun minus(v: Vector) = plus(v.inv())

    fun inv() = Vector(-x, -y)

    infix fun `·`(v: Vector): Double = x * v.x + y * v.y

    fun isVertical(v: Vector) = `·`(v) == 0.0
    infix fun `⊥`(v: Vector) = isVertical(v)

    infix fun `==λ`(v: Vector) = x * v.y == y * v.x
    infix fun `∥`(v: Vector) = `==λ`(v)

    fun angle(v: Vector) = acos((this `·` v) / norm / v.norm)
    infix fun `∠`(v: Vector) = angle(v)
}
