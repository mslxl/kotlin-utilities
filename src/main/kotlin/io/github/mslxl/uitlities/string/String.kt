package io.github.mslxl.uitlities.string

fun Array<*>.mkString(prefix: String, divider: String, suffix: String): String {
    val str = reduce { acc, any ->
        acc.toString() + divider + any.toString()
    }
    return "$prefix$str$suffix"
}

fun Array<*>.mkString(divider: String): String {
    return mkString("", divider, "")
}

operator fun StringBuilder.plusAssign(str: String) {
    append(str)
}

operator fun StringBuffer.plusAssign(str: String) {
    append(str)
}

operator fun String.times(times: Int): String {
    return buildString {
        repeat(times) {
            append(this@times)
        }
    }
}