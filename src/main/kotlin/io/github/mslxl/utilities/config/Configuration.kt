package io.github.mslxl.utilities.config

typealias ConfigurationValue<T> = (value: T) -> Unit

interface Configuration<T : Any> {
    fun set(key: String, value: T): T
    fun get(key: String, default: T, value: ConfigurationValue<T>)
    fun get(key: String, default: T): T
    fun save()
}

