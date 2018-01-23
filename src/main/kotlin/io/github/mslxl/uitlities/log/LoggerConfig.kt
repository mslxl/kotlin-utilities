package io.github.mslxl.uitlities.log

import java.text.DateFormat
import java.text.SimpleDateFormat

open class LoggerConfig {
    var time = false
    var startTime = false
    var timeFormat: DateFormat = SimpleDateFormat("MM-dd HH:mm:ss")
    var tag = true
}