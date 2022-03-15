package ru.tinkoff.fintech.homework.lesson3

import io.kotest.core.spec.style.FeatureSpec
import io.kotest.matchers.ints.exactly
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import ru.tinkoff.fintech.homework.lesson1.*

class HuntingTests : FeatureSpec() {
    val chicken = mockk<Chicken>()
    val cat = mockk<Cat>()

    init {
        feature("Тестирование охоты") {
            scenario("Охотник не может убить, мёртвую курицу") {
                val hunter = Hunter(10, 10)
                val hunting = Hunting(hunter, chicken);
                every { chicken.isAlive() } returns false

                hunting.huntersShoot() shouldBe false
            }

            scenario("Мёртвый кот не может охотиться за курицей") {
                val chicken = Chicken(5, 3)
                val hunting = Hunting(cat, chicken)
                every { cat.isAlive() } returns false

                hunting.huntersShoot() shouldBe false
            }

            scenario("При дефолтном расстоянии между животными, хищник может убить жертву с вероятностью 100%") {
                val chicken = Chicken(10, 22)
                val hunting = Hunting(cat, chicken)
                every { cat.isAlive() } returns true
                every { cat.addEnergy(chicken.weight) } returns Unit

                hunting.huntersShoot() shouldBe true
            }


        }
    }
}
