package ru.tinkoff.fintech.homework.lesson4

import java.util.*
import kotlin.math.max

class Stack<T> {
    private var elements: Array<Any> = arrayOf()
    private var size = 0

    private fun ensureCapacity() {
        if (elements.size == size) {
            elements = Arrays.copyOf(elements, max(size * 2, 1))
        }
    }

    fun push(element: T) {
        ensureCapacity()
        elements[size++] = (element as T)!!
    }

    fun pop(): T {
        return elements[--size] as T
    }

    fun element(): T {
        if (size == 0) error ("Нет элементов в Стэке")
        return elements[size - 1] as T
    }

    fun size(): Int {
        return size
    }

    fun isEmpty(): Boolean {
        return size == 0
    }

    fun clear() {
        elements = arrayOf()
        size = 0
    }
}