import org.junit.Assert

infix fun Any.shouldBe(e: Any) {
    Assert.assertEquals(e, this)
}