package io.github.mslxl.utilities.net

import java.io.InputStream

interface ServerResourceProcessor {
    fun process(questQuestHeader: HttpQuestHeader, data: InputStream, respond: HttpRespondHeader): InputStream?
}