@file:Suppress("NOTHING_TO_INLINE")

package io.github.mslxl.utilities.property

import com.google.gson.annotations.Expose

typealias PropertyLinstener<T> = (old: T, new: T) -> Unit

open class Property<T : Any>(@Expose(serialize = true, deserialize = true) private var property: T) : Cloneable {

    // Gson 会赋值为 Null 真是太感人了
    @Expose(serialize = false, deserialize = false)
    @Volatile
    private var listeners: ArrayList<PropertyLinstener<T>>? = null


    val value get():T = property
    inline fun <R> value(block: (T) -> R): R? = value.let(block)

    fun set(value: T) {
        onChange(this.value, value)
        property = value
    }

    fun listener(listener: PropertyLinstener<T>) {
        if (listeners == null) {
            listeners = ArrayList()
        }
        listeners!!.add(listener)
    }

    fun bind(property: Property<T>) = bind(property) { return@bind it }
    fun bind(property: Property<T>, before: (T) -> T) {
        property.listener { old, new ->
            set(before.invoke(new))
        }
    }

    @Synchronized
    private fun onChange(old: T, new: T) {
        listeners?.forEach {
            try {
                it.invoke(old, new)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun equals(other: Any?): Boolean {
        return value == other
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    override fun toString(): String {
        return value.toString()
    }

    public override fun clone(): Property<T> {
        @Suppress("UNCHECKED_CAST")
        return super.clone() as Property<T>
    }
}

// String
inline operator fun Property<String>.plus(string: String): Property<String> = this.clone().apply {
    this += string
}

inline operator fun Property<String>.plus(property: Property<String>): Property<String> = this.clone().apply {
    this += property.value
}

inline operator fun Property<String>.plusAssign(string: String) = set(value + string)
inline operator fun Property<String>.plusAssign(property: Property<String>) = set(value + property.value)