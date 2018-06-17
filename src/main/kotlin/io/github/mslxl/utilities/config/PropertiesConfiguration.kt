package io.github.mslxl.utilities.config

import io.github.mslxl.utilities.logic.StatisticsMap
import java.io.File
import java.util.*
import kotlin.collections.HashMap

class PropertiesConfiguration(private val file: File) : Configuration<String> {

    private val properties: Properties = Properties()

    init {
        file.reader().use {
            properties.load(it)
        }
    }

    private val map = StatisticsMap<String, LinkedList<ConfigurationValue<String>>, HashMap<String, LinkedList<ConfigurationValue<String>>>>(HashMap()) {
        LinkedList()
    }

    override fun set(key: String, value: String): String {
        properties.setProperty(key, value)
        try {
            if (key in map) map[key].forEach {
                it.invoke(value)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return value
    }

    override fun get(key: String, default: String, value: ConfigurationValue<String>) {
        try {
            if (key in properties)
                value.invoke(properties.getProperty(key))
            else
                value.invoke(default)

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun get(key: String, default: String): String {
        return if (key in properties) {
            properties.getProperty(key)
        } else {
            set(key, default)
            default
        }
    }

    override fun save() {
        save("")
    }

    fun save(comment: String) {
        file.writer().use {
            properties.store(it, comment)
        }
    }

}