package io.github.mslxl.utilities.num

import io.github.mslxl.utilities.log.log
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
            "There are ${u.count} of $t".log("RandomTest")
        }

    }
}