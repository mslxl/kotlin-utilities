package io.github.mslxl.utilities.simplehttp

import io.github.mslxl.utilities.logic.loop
import java.io.File
import java.net.ServerSocket
import java.net.Socket
import kotlin.concurrent.thread

@Suppress("MemberVisibilityCanBePrivate")
class HttpSimpleServer(@Suppress("CanBeParameter") val port: Int = 80) {
    private val server = ServerSocket(port)
    private val resourcesTree = HashMap<String, ServerResourceProcessor>()

    init {
        addResourceProcessor("/404", HttpResNotFoundProcessor())
    }

    fun start() {
        listenCilent()
    }

    private fun listenCilent() {
        thread {
            loop {
                val socket = server.accept()
                communicationWith(socket)
            }
        }
    }

    private fun communicationWith(socket: Socket) {
        thread {
            val input = socket.getInputStream()
            val reader = input.bufferedReader()

            val header = HttpRequestHeader.parse(
                    buildString {
                        var line = ""
                        while (reader.readLine()?.let { line = it;it.isNotBlank() } == true) {
                            appendln(line)
                        }
                    }
            )

            val requestUrl = header.url.substringBeforeLast('?')
            val processor = if (resourcesTree.containsKey(requestUrl))
                resourcesTree[requestUrl]!!
            else
                resourcesTree["/404"]!!


            val respond = HttpRespondHeader(200, "OK")
            val stream = processor.process(header, input, respond)
            if (stream != null) {
                respond["Content-Length"] = stream.available().toString()
            }

            val client = socket.getOutputStream()
            client.bufferedWriter().run {
                write(respond.toString())
                write("\r\n")
                flush()
            }
            stream?.copyTo(client)
            stream?.close()
            socket.close()
        }
    }

    @JvmOverloads
    fun addFileResource(file: File, path: String = file.path) {
        resourcesTree[path] = HttpServerFileResource(file)
    }

    fun addResourceProcessor(path: String, processor: ServerResourceProcessor) {
        resourcesTree[path] = processor
    }

}