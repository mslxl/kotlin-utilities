package io.github.mslxl.uitlities.logic

inline fun Boolean.isTrue(block: () -> Unit): Boolean {
    if (this) block.invoke()
    return this
}

inline fun Boolean.isFalse(block: () -> Unit): Boolean {
    if (!this) block.invoke()
    return this
}

inline fun <T : Any?> T.isNull(block: () -> Unit): T {
    if (this == null) block.invoke()
    return this
}

inline fun <T : Any> T?.isNotNull(block: (T) -> Unit): T? {
    if (this != null) block.invoke(this)
    return this
}

inline fun loop(block: () -> Unit) {
    while (true) {
        block.invoke()
    }
}