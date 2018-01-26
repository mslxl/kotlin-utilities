package io.github.mslxl.uitlities.logic

import io.github.mslxl.uitlities.Block
import io.github.mslxl.uitlities.BlockWithArgs
import org.jetbrains.annotations.NotNull

inline fun Boolean.isTrue(block: Block): Boolean {
    if (this) block.invoke()
    return this
}

inline fun Boolean.isFalse(block: Block): Boolean {
    if (!this) block.invoke()
    return this
}

inline fun <T : Any?> T.isNull(block: Block): T {
    if (this == null) block.invoke()
    return this
}

inline fun <T : Any> T?.isNotNull(@NotNull block: BlockWithArgs<T>): T? {
    if (this != null) block.invoke(this)
    return this
}