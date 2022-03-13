package ru.tinkoff.fintech.homework.lesson3

import io.kotest.core.spec.style.FeatureSpec
import io.kotest.matchers.ints.exactly
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import ru.tinkoff.fintech.homework.lesson1.*

class HuntingTests : FeatureSpec() {
    init {
        val chicken = mockk<Chicken>()
        val cat = mockk<Cat>()

        feature("Тестирование охоты") {
            scenario("Охотник стреляет в курицу") {
                val hunter = Hunter(10, 10)
                val hunting = Hunting(hunter, chicken);
                every { chicken.isAlive() } returns false

                hunting.huntersShoot() shouldBe false
                verify(exactly = 1) { hunting.huntersShoot() }
            }

            scenario("Кот охотиться за курицей") {
                val chicken = Chicken(5, 3)
                val hunting = Hunting(cat, chicken)
                every { cat.isAlive() } returns false

                hunting.huntersShoot() shouldBe false
                verify(exactly = 1) { hunting.huntersShoot() }
            }

            scenario("Тестирование охоты вплотную") {
                val chicken = Chicken(10, 22)
                val hunting = Hunting(cat, chicken)
                every { cat.isAlive() } returns true
                every { cat.addEnergy(chicken.weight) } returns Unit

                hunting.huntersShoot() shouldBe true
                hunting.huntersShoot() shouldBe false
                verify(exactly = 2) { hunting.huntersShoot() }
                verify(exactly = 2) { cat.isAlive() }
            }

            scenario("10 пробежек кота") {
                every { cat.run() } returns Unit

                repeat(10) {
                    cat.run()
                }

                verify(exactly = 10) { cat.run() }
            }
        }
    }
}
