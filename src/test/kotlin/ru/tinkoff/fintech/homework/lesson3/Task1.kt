package ru.tinkoff.fintech.homework.lesson3

import io.kotest.core.spec.style.FeatureSpec
import io.kotest.matchers.ints.shouldBeInRange
import io.kotest.matchers.shouldBe
import ru.tinkoff.fintech.homework.lesson1.*

class Task1 : FeatureSpec(){
    init {
        feature("Бестируем бег кота") {
            scenario("Небольшая пробежка") {
                val cat = Cat(10, 20)
                cat.run()
                cat.energy shouldBeInRange 1..99
            }
            scenario("Бег до смерти") {
                val cat = Cat(10, 20)
                for (i in 1..10) {
                    cat.run()
                }
                cat.energy shouldBe 0
                cat.isAlive() shouldBe false
            }
        }
    }
}
