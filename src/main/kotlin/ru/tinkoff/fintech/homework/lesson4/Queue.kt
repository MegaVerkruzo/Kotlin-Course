package ru.tinkoff.fintech.homework.lesson4

import java.util.*
import kotlin.math.max

class Queue<T : Any> {
    private lateinit var elements: Array<T?>
    private var head = 0
    private var tail = 0
    var size = 0
        private set

    fun offer(element: T): Boolean {
        ensureCapacity()
        if (tail == head && size > 0) {
            System.arraycopy(elements, 0, elements, elements.size / 2, tail)
            tail += elements.size / 2
        }
        size += 1
        elements[tail] = element
        tail = nextElement(tail)
        return true
    }

    fun element(): T? {
        if (size == 0) NoSuchElementException()
        return elements[head]
    }

    fun peek(): T? {
        if (size == 0) NoSuchElementException()
        return elements[head]
    }

    fun remove(): T? {
        if (size == 0) NoSuchElementException()
        val result: T? = elements[head]
        size -= 1
        head = nextElement(head)
        return result
    }

    fun poll(): T? {
        if (size == 0) NoSuchElementException()
        val result: T? = elements[head]
        size -= 1
        head = nextElement(head)
        return result
    }

    private fun ensureCapacity() {
        if (elements.size == size) {
            elements = elements.copyOf(max(size * 2, 1))
        }
    }

    private fun nextElement(index: Int): Int {
        return (index + 1) % elements.size
    }
}