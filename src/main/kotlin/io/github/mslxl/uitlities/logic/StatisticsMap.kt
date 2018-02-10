package io.github.mslxl.uitlities.logic

import java.util.*
import kotlin.collections.HashMap

class StatisticsMap<K : Any, V : Any>() {
    private val hashMap = HashMap<K, LinkedList<V>>()

    operator fun get(key: K): List<V> {
        return if (hashMap.containsKey(key))
            hashMap[key]!!
        else
            emptyList()
    }

    fun add(key: K, item: V) {
        if (!hashMap.containsKey(key)) {
            hashMap[key] = LinkedList()
        }
        hashMap[key]!!.add(item)
    }

    val keys get() = hashMap.keys.toSet()

    inline fun forEach(block: (key: K, list: List<V>) -> Unit) {
        keys.forEach { key ->
            val list = get(key)
            block.invoke(key, list)
        }
    }
}