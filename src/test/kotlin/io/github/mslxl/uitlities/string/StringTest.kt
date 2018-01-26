package io.github.mslxl.uitlities.string

import io.kotlintest.matchers.shouldBe
import org.junit.Test

class StringTest {

    @Test
    fun mkString() {
        arrayOf("h", "e", "l", "l", "o").mkString("[", ",", "]") shouldBe "[h,e,l,l,o]"
        arrayOf("h", "e", "l", "l", "o").mkString(",") shouldBe "h,e,l,l,o"
    }
}