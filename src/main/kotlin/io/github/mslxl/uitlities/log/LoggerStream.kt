package io.github.mslxl.uitlities.log

import java.io.PrintStream
import java.util.*

internal class LoggerStream(output: PrintStream) : PrintStream(output) {
    override fun print(p0: Boolean) {
        p0.log("Std",nextLine = false)
    }

    override fun print(p0: Char) {
        p0.log("Std",nextLine = false)
    }

    override fun print(p0: Int) {
        p0.log("Std",nextLine = false)
    }

    override fun print(p0: Long) {
        p0.log("Std",nextLine = false)
    }

    override fun print(p0: Float) {
        p0.log("Std",nextLine = false)
    }

    override fun print(p0: Double) {
        p0.log("Std",nextLine = false)
    }

    override fun print(p0: CharArray?) {
        p0.toString().log("Std",nextLine = false)
    }

    override fun print(p0: String?) {
        p0.log("Std",nextLine = false)
    }

    override fun print(p0: Any?) {
        p0.log("Std",nextLine = false)
    }

    override fun println(p0: Boolean) {
        p0.log("Std")
    }

    override fun println(p0: Char) {
        p0.log("Std")
    }

    override fun println(p0: Int) {
        p0.log("Std")
    }

    override fun println(p0: Long) {
        p0.log("Std")
    }

    override fun println(p0: Float) {
        p0.log("Std")
    }

    override fun println(p0: Double) {
        p0.log("Std")
    }

    override fun println(p0: CharArray?) {
        p0.log("Std")
    }

    override fun println(p0: String?) {
        p0.log("Std")
    }

    override fun println(p0: Any?) {
        p0.log("Std")
    }

    override fun printf(p0: String?, vararg p1: Any?): PrintStream {
        (p0?.format(p1)?:"null").log("Std")
        return this
    }

    override fun printf(p0: Locale?, p1: String?, vararg p2: Any?): PrintStream {
        (p1?.format(p0,p2)?:"null").log("Std")
        return this
    }
}