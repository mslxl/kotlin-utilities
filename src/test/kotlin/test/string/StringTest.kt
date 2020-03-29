package test.string


import org.junit.Test
import shouldBe

class StringTest {

    @Test
    fun formatByPair() {
        "hello {{name}},are u {{quest}},bye {{name}}".formatByPair("name" to "van", "quest" to "ok") shouldBe "hello van,are u ok,bye van"
    }
}