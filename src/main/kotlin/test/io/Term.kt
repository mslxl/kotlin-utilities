@file:Suppress("NOTHING_TO_INLINE", "unused")

package test.io


import java.io.IOException
import java.io.InputStream
import java.nio.charset.Charset
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.thread

fun shell(command: String, charset: Charset = Charset.defaultCharset()) = ShellProcess(Runtime.getRuntime().exec(command), charset)
fun shell(file: String, vararg args: String, charset: Charset = Charset.defaultCharset()) = ShellProcess(Runtime.getRuntime().exec(arrayOf(file, *args)), charset)

typealias OutListener = (line: String) -> Unit

@Suppress("MemberVisibilityCanBePrivate")
class ShellProcess internal constructor(val process: Process, val charset: Charset) {
    private val lock = ReentrantLock()
    private val stdIn by lazy {
        process.outputStream.bufferedWriter(charset)
    }

    private fun readFromStreamIfAlive(inputStream: InputStream, listener: OutListener) {
        do {
            if (inputStream.available() > 0)
                inputStream.readBytes().toString(charset).let(listener)
            Thread.sleep(500)
        } while (process.isAlive)
    }

    fun listenStandOut(listener: OutListener) {
        thread {
            var needUnlock = false
            if (!lock.isLocked) {
                // 只需要 [listenStandOut] 或 [listenErrorOut] 其中一个拥有锁
                lock.lock()
                needUnlock = true
            }
            Thread.yield()
            readFromStreamIfAlive(process.inputStream, listener)
            if (needUnlock) {
                lock.unlock()
            }
        }
    }

    fun listenErrorOut(listener: OutListener) {
        thread {
            var needUnlock = false
            if (!lock.isLocked) {
                // 只需要 [listenStandOut] 或 [listenErrorOut] 其中一个拥有锁
                lock.lock()
                needUnlock = true
            }
            Thread.yield()
            readFromStreamIfAlive(process.errorStream, listener)
            if (needUnlock) {
                lock.unlock()
            }
        }
    }

    fun listenOut(listener: OutListener) {
        listenErrorOut(listener)
        listenStandOut(listener)
    }

    private inline fun waitAndReadCore(function: (OutListener) -> Unit): String {
        val builder = StringBuilder()
        function {
            builder.append(it)
        }
        while (!lock.isLocked) Thread.yield()
        return try {
            lock.lock()
            builder.toString()
        } finally {
            lock.unlock()
        }
    }

    fun waitAndReadStand(): String {
        return waitAndReadCore(this::listenStandOut)
    }

    fun waitAndReadError(): String {
        return waitAndReadCore(this::listenErrorOut)
    }

    fun waitAndReadOut(): String {
        return waitAndReadCore(this::listenOut)
    }

    fun input(text: String) {
        stdIn.write(text)
    }

    fun EOF() {
        try {
            stdIn.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun stop() = process.destroy()

    fun waitListenerThread() {
        while (!lock.isLocked)
            Thread.yield()
    }
}