package io.github.mslxl.uitlities.io

import java.io.InputStream

inline fun <R> InputStream.use(block: (InputStream) -> R): R {
    val ret = block.invoke(this)
    close()
    return ret
}