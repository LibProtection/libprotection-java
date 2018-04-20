package org.libprotection.injections.utils

class Stack<T>{
    private val elements: MutableList<T> = mutableListOf()

    fun isEmpty() = elements.isEmpty()

    fun count() = elements.size

    fun push(item: T) = elements.add(item)

    fun pop() : T? {
        val item = elements.lastOrNull()
        if (!isEmpty()){
            elements.removeAt(elements.size -1)
        }
        return item
    }

    fun peek() = elements.last()

    override fun toString(): String = elements.toString()
}