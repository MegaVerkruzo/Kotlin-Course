package ru.tinkoff.fintech.homework.lesson4

import java.util.*

class Queue<T> {
    private var elements: MutableList<T> = mutableListOf()
    private var size = 0
    private var head = 0
    private var tail = 0

    private fun ensureCapacity() {
        if (elements.size = size) {
            elements = elements.copyOf( size * 2)
        }
    }
}