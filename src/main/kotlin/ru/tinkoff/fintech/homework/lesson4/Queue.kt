package ru.tinkoff.fintech.homework.lesson4

import java.util.*
import kotlin.math.max

class Queue<T : Any> {
    private class Node<T>(val value: T, var prev: Node<T>?)

    private var top: Node<T>? = null
    private var back: Node<T>? = null
    var size = 0
        private set

    fun element(): T {
        if (size == 0) NoSuchElementException()

        return top!!.value
    }

    fun remove(): T {
        if (size == 0) NoSuchElementException()
        val result = top!!.value
        top = top!!.prev
        size--
        return result
    }

    fun peek(): T? {
        if (size == 0) return null

        return top!!.value
    }

    fun poll(): T? {
        if (size == 0) return null
        val result = top!!.value
        top = top!!.prev
        size--
        return result
    }

    fun offer(element: Any): Boolean {
        if (element as? T == null) {
            return false
        }
        if (size == 0) {
            top = Node(element, null)
        } else {
            val newNode = Node(element, null)
            if (size == 1) {
                back = newNode
                top!!.prev = back
            } else {
                back!!.prev = newNode
                back = newNode
            }
        }
        size++
        return true
    }

    fun isEmpty(): Boolean {
        return size == 0
    }
}