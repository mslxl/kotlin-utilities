package io.github.mslxl.utilities.io

import io.github.mslxl.utilities.logic.loop
import java.io.File
import java.nio.file.FileSystems
import java.nio.file.StandardWatchEventKinds
import java.nio.file.WatchEvent
import kotlin.concurrent.thread

object Files {
    private fun watchFile(file: File, onChange: (context: Any, WatchEvent.Kind<*>) -> Unit) {
        val service = FileSystems.getDefault().newWatchService()
        val path = FileSystems.getDefault().getPath(file.absolutePath)
        val key = path.register(service, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY)
        thread {
            loop {
                for (event in key.pollEvents()) {
                    onChange.invoke(event.context(), event.kind())
                    TODO()
                }
                key.reset()
            }
        }
    }
}
