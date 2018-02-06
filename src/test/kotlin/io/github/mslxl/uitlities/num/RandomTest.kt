package io.github.mslxl.uitlities.num

import io.github.mslxl.uitlities.log.log
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
        "The number of result:${map.size}".log("RandomTest")
        map.forEach { t, u ->
            "There are ${u.value} of $t".log("RandomTest")
        }

    }
}