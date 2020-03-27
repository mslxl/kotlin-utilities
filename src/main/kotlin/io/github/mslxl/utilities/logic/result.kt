package io.github.mslxl.utilities.logic

sealed class Result<out T : Any> {
    data class Success<out T : Any>(val value: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()


    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[value=$value]"
            is Error -> "Error[$exception]"
        }
    }

    inline fun onSuccess(block: (data: T) -> Unit): Result<T> {
        if (this is Success) {
            block.invoke(value)
        }
        return this
    }

    inline fun onError(block: (e: Exception) -> Unit): Result<T> {
        if (this is Error) {
            block.invoke(exception)
        }
        return this
    }
}