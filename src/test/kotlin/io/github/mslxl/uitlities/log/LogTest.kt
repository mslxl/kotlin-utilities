package io.github.mslxl.uitlities.log

import org.junit.Test

class LogTest {
    @Test
    fun test() {
        DefaultLoggerConfig.time = true
        DefaultLoggerConfig.startTime = true
        "Log test".log("Test")
        println("kotlin.println test")
        log("Vararg test","test 1","test 2","test 3")
    }
}