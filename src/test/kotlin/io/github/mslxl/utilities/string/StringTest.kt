package io.github.mslxl.utilities.string

import io.kotlintest.matchers.shouldBe
import org.junit.Test

class StringTest {

    @Test
    fun mkString() {
        arrayOf("h", "e", "l", "l", "o").mkString("[", ",", "]") shouldBe "[h,e,l,l,o]"
        arrayOf("h", "e", "l", "l", "o").mkString(divider = ",") shouldBe "[h,e,l,l,o]"
    }

    @Test
    fun formatByPair() {
        "hello {{name}},are u {{quest}},bye {{name}}".formatByPair("name" to "van", "quest" to "ok") shouldBe "hello van,are u ok,bye van"
    }
}