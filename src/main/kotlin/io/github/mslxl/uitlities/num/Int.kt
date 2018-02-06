package io.github.mslxl.uitlities.num

import java.util.*

private var randomTimes: Long = 1

fun random(min: Int,
           max: Int,
           seed: Long = random(0,
                   random(0,
                           random(1000000,
                                   Int.MAX_VALUE,
                                   System.currentTimeMillis()
                           ),
                           System.currentTimeMillis() * 200
                   ),
                   System.currentTimeMillis()) * randomTimes * (randomTimes * 4454864)
): Int {
    randomTimes++
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