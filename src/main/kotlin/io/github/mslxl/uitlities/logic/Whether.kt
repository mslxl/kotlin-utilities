package io.github.mslxl.uitlities.logic

class Whether<T>(val source: T, val condition: Boolean) {
    inline fun isTrue(block: T.() -> Unit): Whether<T> {
        condition.isTrue { block.invoke(source) }
        return this
    }

    inline fun isFalse(block: T.() -> Unit): Whether<T> {
        condition.isFalse { block.invoke(source) }
        return this
    }

    operator fun invoke(): T = source
    val none = Unit
}

inline fun <T> T.whether(condition: T.() -> Boolean): Whether<T> {
    return Whether(this, condition.invoke(this))
}
