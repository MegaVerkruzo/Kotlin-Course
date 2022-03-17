package ru.tinkoff.fintech.homework.lesson4

import java.util.*
import kotlin.math.max

class Stack<T : Any> {
    private var elements: Array<Any?> = arrayOf()
    var size = 0
        private set

    fun push(element: T) {
        ensureCapacity()
        elements[size++] = element
    }

    fun pop(): T {
        if (size == 0) throw NoSuchElementException()
        val result = elements[--size]
        elements[size] = null
        return result as T
    }

    fun peek(): T {
        if (size == 0) throw NoSuchElementException()
        return elements[size - 1] as T
    }

    fun isEmpty(): Boolean = size == 0

    private fun ensureCapacity() {
        if (elements.size == size) {
            elements = elements.copyOf(max(size * 2, 1))
        }
    }
}