package io.github.mslxl.utilities.simplehttp

import java.io.File
import java.io.InputStream

internal class HttpServerFileResource(private val file: File) : ServerResourceProcessor {
    override fun process(requestRequestHeader: HttpRequestHeader, data: InputStream, respond: HttpRespondHeader): InputStream? {
        return file.inputStream()
    }

}