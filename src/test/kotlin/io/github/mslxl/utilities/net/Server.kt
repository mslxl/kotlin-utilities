package io.github.mslxl.utilities.net

import java.io.InputStream

class Server {
    val tag = System.currentTimeMillis().toString()
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Server().http().start()
        }
    }

    fun http(): HttpSimpleServer {
        return HttpSimpleServer(8080).apply {
            addResourceProcessor("/test", object : ServerResourceProcessor {
                override fun process(requestRequestHeader: HttpRequestHeader, data: InputStream, respond: HttpRespondHeader): InputStream? {
                    respond["test"] = tag
                    return "<html><body><h1>POI!</h1>$requestRequestHeader</body></html>".byteInputStream()
                }
            })
        }
    }

}