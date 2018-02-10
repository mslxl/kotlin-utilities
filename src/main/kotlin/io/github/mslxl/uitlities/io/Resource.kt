package io.github.mslxl.uitlities.io

import io.github.mslxl.uitlities.logic.isNotNull
import io.github.mslxl.uitlities.logic.isNull
import io.github.mslxl.uitlities.logic.whether
import java.io.File
import java.io.InputStream
import java.net.URI
import java.net.URL

class Resource() {
    companion object {
        private val resourcesDir = File(".resources").apply {
            whether { (!exists()) || (!isDirectory) }.isTrue { mkdirs() }
        }
    }

    private lateinit var relativePathRW: String
    val path: String by lazy { file.absolutePath }
    val file: File by lazy { File(resourcesDir, relativePath) }
    val relativePath get() = relativePathRW
    val uri: URI by lazy { file.toURI() }
    val url: URL by lazy { uri.toURL() }
    private val checkFile: File by lazy { File(resourcesDir, "$path.md5").whether { exists() }.isFalse { createNewFile() }.source }
    inline fun <R> inputStream(block: (InputStream) -> R): R = file.inputStream().use(block)

    constructor(pathInClasspath: String) : this() {
        relativePathRW = pathInClasspath
        mkParentDirs()
        file.whether { exists() }.isFalse {
            Thread.currentThread().contextClassLoader.getResourceAsStream(relativePath).isNotNull {
                it.use {
                    createNewFile()
                    val output = file.outputStream()
                    it.copyTo(output)
                }
            }.isNull {
                        error("$pathInClasspath not exists, check it exists or be loaded.")
                    }
        }
    }

    constructor(address: URL, requestProperties: Map<String, String>) : this() {
        relativePathRW = "${address.host}/${address.path}"
        mkParentDirs()
        file.whether { exists() }.isFalse {
            address.openConnection().apply {
                requestProperties.forEach { k, v -> this.setRequestProperty(k, v) }
            }.getInputStream().use {
                createNewFile()
                val output = file.outputStream()
                it.copyTo(output)
                output.close()
            }
        }
    }

    constructor(address: URL) : this(address, HashMap<String, String>())

    private fun mkParentDirs() {
        file.parentFile.whether { exists() }.isFalse { mkdirs() }
    }
}