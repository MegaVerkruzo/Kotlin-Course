package ru.tinkoff.fintech.homework.lesson4

import io.kotest.core.spec.style.FeatureSpec
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe

class QueueTest : FeatureSpec() {

    private fun checkQueue(array: Array<Int>, queue: Queue<Int>): Boolean {
        (array.indices).forEach() { i ->
            if (queue.peek() == null && array[i] != queue.remove()) {
                return false
            }
        }
        return true
    }

    init {
        feature("Тестирование изменения очереди") {
            scenario("Добавление и удаление") {
                val queue = Queue<Int>()

                queue.offer(1)
                queue.offer(2)
                queue.offer(3)
                queue.remove()
                queue.remove()
                queue.remove()
                queue.offer(1)
                queue.offer(2)
                queue.offer(3)

                checkQueue(arrayOf(1, 2, 3), queue) shouldBe true
            }
        }
    }
}