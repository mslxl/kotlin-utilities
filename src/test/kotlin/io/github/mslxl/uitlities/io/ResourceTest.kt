package io.github.mslxl.uitlities.io

import io.github.mslxl.uitlities.log.log
import org.junit.AfterClass
import org.junit.Test
import java.io.File
import java.net.URL

class ResourceTest {
    companion object {
        @AfterClass
        @JvmStatic
        fun removeAll(){
            log("Resource Test","Remove `.resources` dir")
            File(".resources").deleteRecursively()
        }
    }
    @Test
    fun url(){
        Resource(URL("https://www.baidu.com/img/bd_logo1.png")).path.log("Resource Test")
    }
    @Test
    fun jar(){
        Resource("resource/test.lice").inputStream {
            readLine().log("Resource Test")
        }
    }
}