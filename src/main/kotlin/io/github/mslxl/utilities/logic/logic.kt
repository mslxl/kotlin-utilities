package io.github.mslxl.utilities.logic


fun <T : Any?> T.isNull(): Boolean {
    return this == null
}

fun <T : Any> T?.isNotNull(): Boolean {
    return isNull().not()
}


inline fun loop(block: () -> Unit) {
    while (true) {
        block.invoke()
    }
}