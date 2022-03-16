package ru.tinkoff.fintech.homework.lesson4

import java.util.*
import kotlin.math.max

class Queue<T : Any> {
    private class Node<T>(val value: T, var prev: Node<T>) {
    }

    private lateinit var top: Node<T>
    private lateinit var back: Node<T>
    private var size: Int = 0

    fun element(): T {
        if (size == 0) NoSuchElementException()

        return top.value
    }

    fun remove(): T {
        if (size == 0) NoSuchElementException()
        // Дописать
    }

    fun peek(): T? {
        if (size == 0) return null

        return top.value
    }

    fun poll(): T? {
        if (size == 0) NoSuchElementException()
        // Дописать
    }

    fun offer(element: Any) {
        if (ele)
    }
}