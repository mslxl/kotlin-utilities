package test.io

import io.github.mslxl.utilities.SysProperty.OS_TYPE
import io.github.mslxl.utilities.io.shell
import io.github.mslxl.utilities.os.OS
import org.junit.Test
import shouldBe

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