package io.github.mslxl.uitlities

import io.github.mslxl.uitlities.log.log
import io.github.mslxl.uitlities.logic.isTrue

inline fun <R> catch(log: Boolean = true, tag: String = "Exception", block: () -> R): R? {
    try {
        return block.invoke()
    } catch (e: Exception) {
        log.isTrue {
            e.log(tag)
        }
        return null
    }
}