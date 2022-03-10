package ru.tinkoff.fintech.homework.lesson4

import java.util.*
import kotlin.math.max

class Queue<T> {
    private var elements: Array<Any> = arrayOf()
    private var size = 0
    private var head = 0
    private var tail = 0

    private fun ensureCapacity() {
        if (elements.size == size) {
            elements = arrayOf(elements.copyOf(max(size * 2, 1)))
        }
    }

    private fun nextElement(index: Int): Int {
        return (index + 1) % elements.size
    }

    fun enqueue(element: T) {
        ensureCapacity()
        if (tail == head && size > 0) {
            System.arraycopy(elements, 0, elements, elements.size / 2, tail)
            tail += elements.size / 2
        }
        size += 1
        elements[tail] = element!!
        tail = nextElement(tail)
    }

    fun element(): T {
        if (size == 0) error("Нет элементов в очереди")
        return elements[head] as T
    }

    fun dequeue(): T {
        if (size == 0) error("Нет элементов в очереди")
        val result: T = elements[head] as T
        size -= 1
        head = nextElement(head)
        return result
    }

    fun size(): Int {
        return size
    }

    fun isEmpty(): Boolean {
        return size == 0
    }

    fun clear() {
        elements = arrayOf()
        head = 0
        tail = 0
        size = 0
    }
}