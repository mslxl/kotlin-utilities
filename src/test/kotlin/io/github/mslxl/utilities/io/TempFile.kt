package io.github.mslxl.utilities.io

import java.io.File
import java.net.URI

class TempFile : File {
    constructor(p0: String?) : super(p0)
    constructor(p0: String?, p1: String?) : super(p0, p1)
    constructor(p0: File?, p1: String?) : super(p0, p1)
    constructor(p0: URI?) : super(p0)

    init {
        deleteOnExit()
    }
}