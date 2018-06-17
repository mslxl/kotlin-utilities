package io.github.mslxl.utilities.logic

class StatisticsMap<K, V, MAP : MutableMap<K, V>>(private val map: MutableMap<K, V>, private val create: () -> V) : MutableMap<K, V> by map {
    override fun get(key: K): V {
        if (!map.containsKey(key))
            map[key] = create.invoke()
        return map[key]!!
    }

    @Deprecated("Using get instead of getOrDefault", ReplaceWith("get"), DeprecationLevel.ERROR)
    override fun getOrDefault(key: K, defaultValue: V): V {
        return get(key)
    }
}