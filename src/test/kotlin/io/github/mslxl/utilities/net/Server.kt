package io.github.mslxl.utilities.net

class Server {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Server().http()
        }
    }

    fun http() {
        val simpleServer = HttpSimpleServer(8080)
        simpleServer.addResource(Resource("io/github/mslxl/utilities/graphics/Typewriter.class"))
        simpleServer.start()
    }
}