package ru.tinkoff.fintech.homework.lesson3

import io.kotest.core.spec.style.FeatureSpec
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import ru.tinkoff.fintech.homework.lesson1.*

class TestKotlin1 : FeatureSpec(){
    init {
        "Chicken" {
            val chicken = Chicken(3, 3)
            chicken.say() shouldBe "Cock-A-Doodle-Doo"
        }
        "Cat" {
            val cat = Cat(4, 14)
            cat.say() shouldBe "Meow"
        }
    }
}
