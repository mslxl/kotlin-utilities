@file:Suppress("NOTHING_TO_INLINE")

package io.github.mslxl.utilities.property

typealias PropertyLinstener<T> = (old: T, new: T) -> Unit

open class Property<T : Any>(private var property: T) : Cloneable {

    private val listeners = ArrayList<PropertyLinstener<T>>()

    val value get():T = property
    inline fun <R> value(block: (T) -> R): R? = value.let(block)

    fun set(value: T) {
        onChange(this.value, value)
        property = value
    }

    fun listener(listener: PropertyLinstener<T>) {
        listeners.add(listener)
    }

    fun bind(property: Property<T>) = bind(property) { return@bind it }
    fun bind(property: Property<T>, before: (T) -> T) {
        property.listener { old, new ->
            set(before.invoke(new))
        }
    }

    private fun onChange(old: T, new: T) {
        listeners.forEach {
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