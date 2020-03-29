package test.num

import io.github.mslxl.utilities.num.Counter
import io.github.mslxl.utilities.num.random
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