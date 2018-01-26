package io.github.mslxl.uitlities.num

import java.util.*

fun random(min: Int, max: Int, seed: Long = System.currentTimeMillis()): Int {
    return Random(seed).nextInt(max - min) + min
}

operator fun Int.rem(block: () -> Unit) {
    if (this < 101 && this > -1) {
        if (random(0, 100) > this) {
            block.invoke()
        }
    } else {
        error("$this% is not allowed")
    }
}