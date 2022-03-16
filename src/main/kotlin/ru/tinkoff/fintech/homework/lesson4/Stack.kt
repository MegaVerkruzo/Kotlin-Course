package ru.tinkoff.fintech.homework.lesson4

import java.util.*
import kotlin.math.max

class Stack<T : Any> {
    private class Node<T>(val value: T, var prev: Node<T>?)

    private var top: Node<T>? = null
    var size = 0
        private set

    fun push(element: T) {
        top = Node(element, top)
    }

    fun pop(): T? {
        if (size == 0) NoSuchElementException()

        val result = top!!.value
        top = top!!.prev
        return result
    }

    fun peek(): T? {
        if (size == null) NoSuchElementException()

        return top!!.value
    }

    fun isEmpty(): Boolean {
        return size == 0
    }
}