package io.github.mslxl.uitlities.log

import io.github.mslxl.uitlities.BlockWithArgs
import java.io.PrintStream
import java.util.*

internal class LoggerStream(output: PrintStream,val print:BlockWithArgs<Any?>,val println:BlockWithArgs<Any?>) : PrintStream(output) {
    override fun print(p0: Boolean) {
        print.invoke(p0)
    }

    override fun print(p0: Char) {
        print.invoke(p0)
    }

    override fun print(p0: Int) {
        print.invoke(p0)
    }

    override fun print(p0: Long) {
        print.invoke(p0)
    }

    override fun print(p0: Float) {
        print.invoke(p0)
    }

    override fun print(p0: Double) {
        print.invoke(p0)
    }

    override fun print(p0: CharArray) {
        print.invoke(p0)
    }

    override fun print(p0: String?) {
        print.invoke(p0)
    }

    override fun print(p0: Any?) {
        print.invoke(p0)
    }

    override fun println(p0: Boolean) {
        println.invoke(p0)
    }

    override fun println(p0: Char) {
        println.invoke(p0)
    }

    override fun println(p0: Int) {
        println.invoke(p0)
    }

    override fun println(p0: Long) {
        println.invoke(p0)
    }

    override fun println(p0: Float) {
        println.invoke(p0)
    }

    override fun println(p0: Double) {
        println.invoke(p0)
    }

    override fun println(p0: CharArray?) {
        println.invoke(p0)
    }

    override fun println(p0: String?) {
        println.invoke(p0)
    }

    override fun println(p0: Any?) {
        println.invoke(p0)
    }

    override fun printf(p0: String?, vararg p1: Any?): PrintStream {
        println.invoke((p0?.format(p1) ?: "null"))
        return this
    }

    override fun printf(p0: Locale?, p1: String?, vararg p2: Any?): PrintStream {
        println.invoke((p1?.format(p0, p2) ?: "null"))
        return this
    }
}