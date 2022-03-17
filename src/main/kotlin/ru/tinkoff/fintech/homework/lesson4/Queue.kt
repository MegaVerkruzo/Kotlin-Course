package ru.tinkoff.fintech.homework.lesson4

import java.util.*
import kotlin.NoSuchElementException
import kotlin.math.max

class Queue<T : Any> {
    private var elements: Array<Any?> = arrayOf()
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

    fun peek(): T? = if (size == 0) null else elements[head] as T

    fun poll(): T? {
        if (size == 0) return null
        val result = elements[head] as T
        elements[head] = null
        size -= 1
        head = nextElement(head)
        return result
    }

    fun element(): T {
        return peek() ?: throw NoSuchElementException()
    }

    fun remove(): T {
        return poll() ?: throw NoSuchElementException()
    }

    fun isEmpty(): Boolean = size == 0

    private fun ensureCapacity() {
        if (elements.size == size) {
            elements = elements.copyOf(max(size * 2, 1))
        }
    }

    private fun nextElement(index: Int): Int {
        return (index + 1) % elements.size
    }
}