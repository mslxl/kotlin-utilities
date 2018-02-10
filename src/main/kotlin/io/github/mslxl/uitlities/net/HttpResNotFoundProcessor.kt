package io.github.mslxl.uitlities.net

import io.github.mslxl.uitlities.string.times
import java.io.InputStream

internal class HttpResNotFoundProcessor : ServerResourceProcessor {
    override fun process(questQuestHeader: HttpQuestHeader, data: InputStream, respond: HttpRespondHeader): InputStream? {
        respond.code = 404
        respond.msg = "Not Found"
        respond["Content-Type"] = "text/html;charset=UTF-8"
        return """
            <html>
                <body>
                    <center><h1>404 Not Found</h1></center>
                    <hr/>
                    <center><h3>Resource not found:${questQuestHeader.path}</h3></center>
                    <center>${"&nbsp;" * 60}— mslxl's simple server</center>

                </body>
            </html>
        """.trimIndent().byteInputStream()
    }
}