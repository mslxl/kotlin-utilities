package io.github.mslxl.utilities.io

import io.github.mslxl.utilities.OS_TYPE
import io.github.mslxl.utilities.os.OS
import io.kotlintest.matchers.shouldBe
import org.junit.Test

class TermTest {
    @Test
    fun echo() {
        // I only tested linux
        if (OS_TYPE == OS.Linux)
            shell("echo -n test").waitAndReadStand() shouldBe "test"
        if (OS_TYPE == OS.Windows)
            shell("echo test").waitAndReadOut() shouldBe "test\n"
    }
}