package io.github.mslxl.uitlities.net

import io.github.mslxl.uitlities.io.Resource

class Server {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Server().http()
        }
    }

    fun http() {
        val simpleServer = HttpSimpleServer(8080)
        simpleServer.addResource(Resource("resource/test.lice"))
        simpleServer.start()
    }
}