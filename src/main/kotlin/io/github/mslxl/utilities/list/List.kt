@file:Suppress("NOTHING_TO_INLINE")

package io.github.mslxl.utilities.list

inline operator fun <T : java.util.AbstractList<TT>, TT> T.plus(item: Collection<TT>): T {
    addAll(item)
    return this
}

inline operator fun <T : java.util.AbstractList<TT>, TT> T.plus(item: TT): T {
    add(item)
    return this
}

inline operator fun <T : java.util.AbstractList<TT>, TT> T.plus(item: Array<TT>): T {
    addAll(item)
    return this
}