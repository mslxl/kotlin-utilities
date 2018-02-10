package io.github.mslxl.uitlities.net

class HttpQuestHeader {
    companion object {
        private val regex = "(.*):\\s(.*)".toRegex()
        fun parse(header: String): HttpQuestHeader {
            val headerObj = HttpQuestHeader()
            if (header.isBlank()) {
                error("Illegal HTTP headers")
            }
            val firstLine = header.lines().first()

            firstLine.split(' ').apply {
                headerObj.method = component1()
                headerObj.path = component2()
                headerObj.version = component3()
            }

            val result = regex.findAll(header, firstLine.length)

            result.forEach {
                val values = it.groupValues
                headerObj[values.component2()] = values.component3()
            }
            return headerObj
        }
    }

    lateinit var method: String
    lateinit var path: String
    lateinit var version: String
    private val content = HashMap<String, String>()
    operator fun get(key: String): String? {
        return content[key]
    }

    operator fun set(key: String, value: String) {
        content[key] = value
    }

    operator fun contains(key: String): Boolean {
        return content.containsKey(key)
    }

    override fun toString(): String {
        return buildString {
            append(method)
            append(' ')
            append(path)
            append(' ')
            append(version)
            appendln()
            content.forEach { key, value ->
                append(key)
                append(": ")
                append(value)
                appendln()
            }
        }
    }
}