@file:Suppress("NOTHING_TO_INLINE", "unused")

package io.github.mslxl.utilities

import io.github.mslxl.utilities.os.OS


val USER_NAME = property("user.name")

val USER_LANGUAGE_FORMAT = property("user.language.format")

val USER_LANGUAGE = property("user.language")

val USER_DIR = property("user.dir")

val USER_HOME = property("user.home")

val USER_COUNTRY = property("user.country")

val USER_COUNTRY_FORMAT = property("user.country.format")

val FILE_SEPARATOR = property("file.separator")

val FILE_ENCODING = property("file.encoding")

val AWT_HEADLESS = property("java.awt.headless")?.toBoolean() ?: false

val JAVA_DIRS = property("java.ext.dirs")

val JAVA_HOME = property("java.home")

val JAVA_VERSION = property("java.version")

val JAVA_VM_INFO = property("java.vm.info")

val JAVA_VM_NAME = property("java.vm.name")

val JAVA_CLASS_PATH = property("java.class.path")

val JAVA_CLASS_VERSION = property("java.class.version")

val JAVA_RUNTIME_VERSION = property("java.runtime.version")

val JAVA_RUNTIME_NAME = property("java.runtime.name")

val OS_VERSION = property("os.version")

val OS_NAME = property("os.name")

val OS_ARCH = property("os.arch")

val TMPDIR = property("java.io.tmpdir")

val LINE_SEPARATOR = property("line.separator")

val PATH_SEPARATOR = property("path.separator")

val OS_TYPE by lazy {
    when {
        OS_NAME.startsWith("Windows") -> OS.Windows
        OS_NAME.startsWith("Linux") -> OS.Linux
        OS_NAME.startsWith("Mac OS X") -> OS.MacOSX
        OS_NAME.startsWith("FreeBSD") -> OS.FreeBSD
        else -> OS.Unknown
    }
}

private inline fun property(key: String) = System.getProperty(key)