package io.github.mslxl.utilities.num

import org.junit.Test

class RandomTest {
    @Test
    fun randomTest() {
        val map = HashMap<Int, Counter>()
        for (i in 1..10) {
            map[i] = Counter()
        }
        repeat(100) {
            val int = random(1, 10)
            map[int]!!.inc()
        }
    }
}