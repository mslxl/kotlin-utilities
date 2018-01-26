package io.github.mslxl.uitlities.log

import io.github.mslxl.uitlities.BlockWithTwoArgs
import io.github.mslxl.uitlities.catch
import io.github.mslxl.uitlities.logic.isNotNull
import io.github.mslxl.uitlities.string.mkString
import java.io.PrintStream
import java.lang.management.ManagementFactory


private val systemOutStream: PrintStream by lazy {
    // 当 Logger 初始化时拦截 System.out 使其强行经过 log
    val outStream = System.out
    System.setOut(LoggerStream(outStream,{ systemPrint(it)},{it.log("Std")}))
    return@lazy outStream
}
private val systemErrStream: PrintStream by lazy {
    // 当 Logger 初始化时拦截 System.out 使其强行经过 log
    val errStream = System.err
    System.setErr(LoggerStream(errStream,{ systemErrPrint(it)},{it.err("Err")}))
    return@lazy errStream
}

internal fun systemPrint(any: Any?) = systemOutStream.print(any)
internal fun systemPrintln(any: Any) = systemOutStream.println(any)
internal fun systemPrint(charArray: CharArray) = systemOutStream.print(charArray)
internal fun systemErrPrint(any: Any?) = systemErrStream.print(any)
internal fun systemErrPrintln(any: Any) = systemErrStream.println(any)
internal fun systemErrPrint(charArray: CharArray) = systemErrStream.print(charArray)

private fun preLog(tag: String ,config: LoggerConfig = GlobalLoggerConfig):String{
    val textTag = if (config.tag) " [ $tag ]" else ""
    val textTime = if (config.time) " [ ${config.timeFormat.format(System.currentTimeMillis())}]" else ""
    val textStartTime = if (config.startTime) " [ ${System.currentTimeMillis() - ManagementFactory.getRuntimeMXBean().startTime}ms ]" else ""
    return "$textTag$textTime$textStartTime"
}

fun <T : Any?> T.log(tag: String = "Log", config: LoggerConfig = GlobalLoggerConfig): T {
    log(tag, this, config = config)
    return this
}

fun log(tag: String = "Log", vararg msg: Any?, config: LoggerConfig = GlobalLoggerConfig) {
    val text = msg.mkString(" ")
    systemPrintln("${preLog(tag,config)} $text")
    Logger.sendEvent(Logger.Source.STD,text)
}

fun err(tag: String = "Err",vararg msg: Any?, config: LoggerConfig = GlobalLoggerConfig){
    val text = msg.mkString(" ")
    systemErrPrintln("${ preLog(tag, config)} $text")
    Logger.sendEvent(Logger.Source.ERR,text)
}

fun <T : Any?> T.err(tag: String = "Err", config: LoggerConfig = GlobalLoggerConfig): T {
    err(tag, this, config = config)
    return this
}

private fun <T:Throwable> preErr(exception: T):String{
    synchronized(exception){
        return buildString {
            with(exception){
                append(exception::class.java.name)
                append(":")
                append(message)
                appendln(message)
                stackTrace.forEach {element ->
                    appendln("\tat $element")
                }
                suppressed.forEachIndexed { index, element ->
                    if (index==0)append("Suppressed: ")
                    append(preErr(element))
                }
                cause.isNotNull {
                    append("Caused by:")
                    append(preErr(cause!!))
                }
            }
        }
    }
}

fun <T:Exception> T.err(tag: String=this::class.java.simpleName,config: LoggerConfig = GlobalLoggerConfig):T{
    val text = preLog(tag,config)
    val err = preErr(this)
    systemErrPrint("$text $err")
    Logger.sendEvent(Logger.Source.ERR,err)
    return this
}

object Logger{
    enum class Source{
        STD,ERR
    }
    private val listeners = ArrayList<BlockWithTwoArgs<Source,String>>()
    fun listen(listener:BlockWithTwoArgs<Source,String>):Int{
        synchronized(listeners) {
            listeners.add(listener)
            return listener.hashCode()
        }
    }
    fun unlisten(id:Int){
        synchronized(listeners){
            val iterator = listeners.listIterator()
            while (iterator.hasNext()){
                val next = iterator.next()
                if (next.hashCode() == id){
                    iterator.remove()
                    return
                }
            }
        }
    }
    internal fun sendEvent(src:Source,log:String){
        listeners.forEach {
            catch(tag = "Log listener"){
                it.invoke(src,log)
            }
        }
    }
}