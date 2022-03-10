package ru.tinkoff.fintech.homework.lesson3

import io.kotest.core.spec.style.FeatureSpec
import io.kotest.matchers.ints.shouldBeInRange
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.verify
import ru.tinkoff.fintech.homework.lesson1.*

class Task2 : FeatureSpec() {
    init {
        lateinit var chicken: Chicken
//        lateinit var hunter: Hunter


        feature("Тестирование охоты") {
            scenario("Охота на курицу вполтную") {
                every { chicken.energy } returns 80
//                every { hunter.energy } returns 100

                verify { chicken.run() }
            }
//
//            scenario("Бег до смерти") {
//                val cat = Cat(10, 20)
//                for (i in 1..10) {
//                    cat.run()
//                }
//                cat.energy shouldBe 0
//                cat.isAlive() shouldBe false
//            }
        }
    }
}
