package io.github.mslxl.uitlities

import io.github.mslxl.uitlities.logic.isTrue

internal typealias Block = () -> Unit
internal typealias BlockWithArgs<A> = (A) -> Unit
internal typealias BlockWithTwoArgs<A,B> = (A,B) -> Unit
internal typealias BlockWithReturn<R> = () -> R
internal typealias BlockBothArgsAndReturn<A, R> = (A) -> R

inline operator fun <T, R> T.invoke(block:BlockBothArgsAndReturn<T,R>): R {
    return block.invoke(this)
}

inline fun <R> catch(log:Boolean=true, tag:String="Exception", block:BlockWithReturn<R>):R?{
    try {
        return block.invoke()
    }catch (e:Exception){
        log.isTrue {

        }
        return null
    }
}