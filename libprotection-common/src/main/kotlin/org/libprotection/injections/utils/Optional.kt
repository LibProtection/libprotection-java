package org.libprotection.injections.utils

class Optional<T> private constructor(private val _value : T?) where T : Any {

    companion object {
        fun<T> of(value : T) where T : Any = Optional(value)
        fun<T> empty() where T : Any = Optional<T>(null)
    }

    fun isEmpty() = _value === null

    val isPresent : Boolean get() = _value !== null

    val value get() = _value!!

    fun orElse(other : T) = _value ?: other

    fun orElseThrow(exception : () -> Exception) = _value ?: throw exception()

    override fun hashCode() = _value?.hashCode() ?: 0

    override fun equals(other: Any?): Boolean {
        if(other === null) return false
        if(this === other) return true
        if(this::class != other::class) return false

        other as Optional<*>

        return this._value == other._value
    }
}