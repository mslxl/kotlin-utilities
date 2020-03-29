package io.github.mslxl.utilities.num

import java.util.*

fun random(min: Int,
           max: Int,
           seed: Long = System.currentTimeMillis()): Int {
    return Random(seed).nextInt(max - min) + min
}

operator fun Int.rem(block: () -> Unit) {
    if (this < 101 && this > -1) {
        val random = random(0, 100)
        if (random < this) {
            block.invoke()
        }
    } else {
        error("$this% is not allowed")
    }
}