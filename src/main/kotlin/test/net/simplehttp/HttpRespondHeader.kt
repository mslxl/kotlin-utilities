package test.net.simplehttp

class HttpRespondHeader(var code: Int, var msg: String) {
    val version: String = "HTTP/1.1"
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
            append(version)
            append(' ')
            append(code)
            append(' ')
            append(msg)
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