package io.github.mslxl.uitlities.num

class Counter(initValue: Int = 0) {
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
}