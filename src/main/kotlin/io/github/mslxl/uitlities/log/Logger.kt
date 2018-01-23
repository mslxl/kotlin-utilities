@file:JvmName("Logger")

package io.github.mslxl.uitlities.log

import java.io.PrintStream
import java.lang.management.ManagementFactory


private val systemOutStream: PrintStream by lazy {
    // 当 Logger 初始化时拦截 System.out 使其强行经过 log
    val outStream = System.out
    System.setOut(LoggerStream(outStream))
    return@lazy outStream
}

fun systemPrint(any: Any) = systemOutStream.print(any)
fun systemPrintln(any: Any) = systemOutStream.println(any)

fun log(tag: String = "Log", vararg msg: Any?, nextLine: Boolean = true) {
    val text = msg.reduce { acc, any ->
        acc.toString() + " " + any.toString()
    }
    val textTag = if (DefaultLoggerConfig.tag) "[ $tag ]" else ""
    val textTime = if (DefaultLoggerConfig.time) "[ ${DefaultLoggerConfig.timeFormat.format(System.currentTimeMillis())}]" else ""
    val textStartTime = if (DefaultLoggerConfig.startTime) "[ ${System.currentTimeMillis() - ManagementFactory.getRuntimeMXBean().startTime}ms ]" else ""
    systemOutStream.print(" $textTag $textTime $textStartTime $text")
    if (nextLine) systemOutStream.print("\n")
}

fun <T : Any?> T.log(tag: String = "Log", nextLine: Boolean = true): T {
    log(tag, this, nextLine = nextLine)
    return this
}