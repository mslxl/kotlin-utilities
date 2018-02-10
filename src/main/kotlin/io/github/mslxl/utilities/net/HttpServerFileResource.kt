package io.github.mslxl.utilities.net

import java.io.File
import java.io.InputStream

internal class HttpServerFileResource(private val file: File) : ServerResourceProcessor {
    override fun process(questQuestHeader: HttpQuestHeader, data: InputStream, respond: HttpRespondHeader): InputStream? {
        return file.inputStream()
    }

}