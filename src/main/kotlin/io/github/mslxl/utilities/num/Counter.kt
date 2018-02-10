package io.github.mslxl.utilities.num

class Counter(private val initValue: Int = 0) {
    private var value = initValue
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
}