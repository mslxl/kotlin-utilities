package io.github.mslxl.utilities

import io.github.mslxl.utilities.log.log
import io.github.mslxl.utilities.logic.isTrue

inline fun <R> catch(log: Boolean = true, tag: String = "Exception", block: () -> R): R? {
    return try {
        block.invoke()
    } catch (e: Exception) {
        log.isTrue {
            e.log(tag)
        }
        null
    }
}

val Runtime = java.lang.Runtime.getRuntime()

@Deprecated("TODO")
fun doSomeThing() {
}

fun doNoting() {}