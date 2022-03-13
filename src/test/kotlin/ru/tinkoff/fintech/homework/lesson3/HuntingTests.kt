package ru.tinkoff.fintech.homework.lesson3

import io.kotest.core.spec.style.FeatureSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import ru.tinkoff.fintech.homework.lesson1.*

class HuntingTests : FeatureSpec() {
    init {
        feature("Тестирование охоты") {
            scenario("Охотник стреляет в курицу") {
                val chicken = mockk<Chicken>()
                val hunter = Hunter(10, 10)

                val hunting = Hunting(hunter, chicken);
                every { chicken.isAlive() } returns false

                hunting.huntersShoot() shouldBe false
                verify(exactly = 1) { hunting.huntersShoot() }
            }

            scenario("Кот охотиться за курицей") {
                val cat = mockk<Cat>()
                val chicken = Chicken(5, 3)

                val hunting = Hunting(cat, chicken)
                every { cat.isAlive() } returns true

                hunting.huntersShoot() shouldBe true
                verify(exactly = 1) { hunting.huntersShoot() }
            }
        }
    }
}
