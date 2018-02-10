package io.github.mslxl.utilities.logic

import io.github.mslxl.utilities.log.log
import org.junit.Test
import java.io.File

class WhetherTest {
    @Test
    fun whether() {
        File("./deep_dark_fantasy.bug").parentFile
                .whether { exists() }
                .isTrue {
                    "File parent '$absolutePath' exists".log("Whether Test")
                }
                .isFalse {
                    "File parent '$absolutePath' not exists".log("Whether Test")
                }().log("WhetherTest")
    }
}