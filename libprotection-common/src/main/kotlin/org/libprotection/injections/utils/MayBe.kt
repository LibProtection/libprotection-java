package org.libprotection.injections.utils

sealed class MayBe<out T>{
    override fun equals(other: Any?) : Boolean {
        if(other === null) return false
        if(this === other) return true
        if(this::class != other::class) return false

        other as Some<*>
        this as Some<*>

        return this.value == other.value
    }
}

inline fun <T> MayBe<T>.orElse(other : () -> T) = when(this){
    is Some<T> -> value
    else -> other()
}

fun <T> MayBe<T>.orElse(other : T) = when(this){
    is Some<T> -> value
    else -> other
}

inline fun <T> MayBe<T>.orElseThrow(exception : () -> Exception) = when(this){
    is Some<T> -> this.value
    else -> throw exception()
}

object None : MayBe<Nothing>()

data class Some<X>(val value : X) : MayBe<X>()
