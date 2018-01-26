package io.github.mslxl.uitlities.list

operator fun <T : java.util.AbstractList<TT>, TT> T.plus(item: Collection<TT>) {
    addAll(item)
}

operator fun <T : java.util.AbstractList<TT>, TT> T.plus(item: TT): T {
    add(item)
    return this
}

operator fun <T : java.util.AbstractList<TT>, TT> T.plus(item: Array<TT>): T {
    addAll(item)
    return this
}