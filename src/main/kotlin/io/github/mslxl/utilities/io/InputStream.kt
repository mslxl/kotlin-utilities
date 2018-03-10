package io.github.mslxl.utilities.io

import java.io.InputStream
import java.nio.charset.Charset

fun InputStream.readAvailable() = readBytes(available())
fun InputStream.readAvailableAsString(charset: Charset) = readBytes(available()).toString(charset)