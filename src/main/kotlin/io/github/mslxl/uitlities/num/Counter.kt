package io.github.mslxl.uitlities.num

class Counter(initValue: Int = 0) {
    var value = initValue
    fun inc(): Int {
        value++
        return value
    }

}