package io.github.mslxl.uitlities.net

import java.io.InputStream

interface ServerResourceProcessor {
    fun process(questQuestHeader: HttpQuestHeader, data: InputStream, respond: HttpRespondHeader): InputStream?
}