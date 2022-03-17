package ru.tinkoff.fintech.homework.lesson4

import io.kotest.core.spec.style.FeatureSpec
import io.kotest.matchers.shouldBe

class StructuresTest : FeatureSpec() {
    init {
        feature("Тестирование очереди") {
            scenario("Добавление и удаление") {
                val queue = Queue<Int>()

                queue.offer(1)
                queue.offer(2)
                queue.offer(3)
                queue.remove()
                queue.remove()
                queue.offer(1)
                queue.offer(2)
                queue.offer(3)

                queue.remove() shouldBe 3
                queue.remove() shouldBe 1
                queue.remove() shouldBe 2
                queue.remove() shouldBe 3
                queue.poll() shouldBe null
            }
        }

        feature("Тестирование стека") {
            scenario("Добавление и удаление") {
                val stack = Stack<Int>()

                stack.push(1)
                stack.push(2)
                stack.push(3)
                stack.pop()
                stack.push(4)

                stack.pop() shouldBe 4
                stack.pop() shouldBe 2
                stack.pop() shouldBe 1
                stack.isEmpty() shouldBe true

            }
        }
    }
}