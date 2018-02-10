package io.github.mslxl.utilities.log

import io.kotlintest.matchers.shouldBe
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Test

class LogTest {
    companion object {
        var id: Int = 0
        var success = false
        @BeforeClass
        @JvmStatic
        fun listener() {
            "listen log".log("Log Listener Test")
            id = Logger.listen { source, s ->
                val text = source.name + " : " + s.subSequence(0, if (s.length < 30) s.length - 1 else 30)
                systemPrintln(text)
                if ("Log te" in text) success = true
            }
        }

        @AfterClass
        @JvmStatic
        fun after() {
            "unlisted log".log("Log Listener Test")
            Logger.cancel(id)
            success shouldBe true
        }
    }

    @Test
    fun log() {
        "Log test".log("Log Test")
        println("kotlin.println test")
        log("Log Test", "test 1", "test 2", "test 3")
    }

    @Test
    fun err() {
        "Err test".err("Log Test")
        System.err.println("System.err test")
        err("Log Test", "err 1", "err 2", "err 3")
        RuntimeException("Test").err("Log Test")
    }
}