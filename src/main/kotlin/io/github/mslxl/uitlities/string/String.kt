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

fun String.htmlTextFormat(): String {
    val regx = "/\"|&|'|<|>|[\\x00-\\x20]|[\\x7F-\\xFF]|[\\u0100-\\u2700]/g".toRegex()

    val text: String = this.replace(regx) {
        var c = it.value[0].toInt()
        c = if (c == 0x20) 0xA0 else c
        "&#$c;"
    }.replace("&#10;", "<br/>")
    return text
}

fun String.format(vararg pair: Pair<String, Any>): String {
    var str = this
    pair.forEach {
        str = str.replace("\\{\\{${it.first}}}".toRegex(), it.second.toString())
    }
    return str
}