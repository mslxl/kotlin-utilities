package io.github.mslxl.utilities.io

import io.github.mslxl.utilities.log.log
import org.junit.Test
import java.net.URL

class ResourceTest {

    @Test
    fun url() {
        Resource(URL("https://www.baidu.com/img/bd_logo1.png")).path.log("Resource Test")
    }

    @Test
    fun jar() {
        Resource("resource/test.lice").inputStream {
            readLine().log("Resource Test")
        }
    }
}