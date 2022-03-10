package ru.tinkoff.fintech.homework.lesson3

import io.kotest.core.spec.style.FeatureSpec
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.ints.shouldBeInRange
import io.kotest.matchers.shouldBe
import ru.tinkoff.fintech.homework.lesson1.*

class TestKotlin1 : FeatureSpec(){
    init {
        feature("Бег кота") {
            scenario("Немного бега") {
                val cat = Cat(4, 14)
                cat.run()
                cat.energy shouldBeInRange 1..99
            }
            scenario("Бег до смерти") {
                val cat = Cat(4, 14)
                for (i in 1..10) {
                    cat.run()
                }
                cat.energy shouldBe 0
                cat.isAlive() shouldBe false
            }
        }
    }
}
