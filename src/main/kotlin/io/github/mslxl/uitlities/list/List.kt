package io.github.mslxl.uitlities.list

operator fun <T:java.util.AbstractList<TT>,TT> T.plusAssign(item:TT){
    add(item)
}
operator fun <T:java.util.AbstractList<TT>,TT> T.plusAssign(item:Collection<TT>){
    addAll(item)
}
operator fun <T:java.util.AbstractList<TT>,TT> T.plus(item:TT):T{
    add(item)
    return this
}