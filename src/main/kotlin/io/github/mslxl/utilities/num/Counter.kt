package io.github.mslxl.utilities.num

import io.github.mslxl.utilities.logic.StatisticsMap
import java.util.*

class Counter(private val initValue: Int = 0) {
    private var value = initValue
        set(value) {
            map[value].forEach { it.invoke(value) }
            field = value
        }

    private val map = StatisticsMap<Int, LinkedList<(Int) -> Unit>, HashMap<Int, LinkedList<(Int) -> Unit>>>(HashMap()) {
        LinkedList()
    }

    fun inc(): Int {
        return ++value
    }

    fun dec(): Int {
        return --value
    }

    val count get() = value
    override fun toString(): String {
        return count.toString()
    }

    fun default() {
        value = initValue
    }

    fun repeat(block: (Int) -> Unit) {
        repeat(count) { block.invoke(it) }
    }

    fun on(index: Int, block: (Int) -> Unit) {
        map[index].add(block)
    }
}