package ru.tinkoff.fintech.homework.lesson4

import java.util.*
import kotlin.math.max

class Queue<T : Any> {
    private class Node<T>(val value: T, var prev: Node<T>?) {
    }

    private var top: Node<T>? = null
    private var back: Node<T>? = null

    fun element(): T {
        if (top == null) NoSuchElementException()

        return top!!.value
    }

    fun remove(): T {
        if (top == null) NoSuchElementException()
        val result = top!!.value
        top = top!!.prev
        return result
    }

    fun peek(): T? {
        if (top == null) return null

        return top!!.value
    }

    fun poll(): T? {
        if (top == null) null
        val result = top!!.value
        top = top!!.prev
        return result
    }

    fun offer(element: Any): Boolean {
        if (element as? T == null) {
            return false
        }
        if (top == null) {
            top = Node(element, null)
        } else {
            val newNode = Node(element, null)
            if (back == null) {
                back = newNode
                top!!.prev = back
            } else {
                back!!.prev = newNode
                back = newNode
            }
        }
        return true
    }
}