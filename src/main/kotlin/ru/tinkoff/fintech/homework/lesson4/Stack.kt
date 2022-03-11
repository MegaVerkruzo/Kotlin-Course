package ru.tinkoff.fintech.homework.lesson4

import java.util.*
import kotlin.math.max

class Stack<T : Comparable<T>> {
    private lateinit var elements: Array<T?>
    var size = 0
        private set

    private fun ensureCapacity() {
        if (elements.size == size) {
            elements = elements.copyOf(max(size * 2, 1))
        }
    }

    fun push(element: T) {
        ensureCapacity()
        elements[size++] = element
    }

    fun pop(): T? {
        if (size == 0) error ("Нет элементов в Стэке")
        val result = elements[--size]
        elements[size] = null // Не понимаю, как я могу удалять элементы
        return result
    }

    fun peek(): T? {
        if (size == 0) error ("Нет элементов в Стэке")
        return elements[size - 1]
    }
}