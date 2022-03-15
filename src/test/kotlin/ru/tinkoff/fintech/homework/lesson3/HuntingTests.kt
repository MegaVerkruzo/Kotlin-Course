package ru.tinkoff.fintech.homework.lesson3

import io.kotest.core.spec.style.FeatureSpec
import io.kotest.matchers.ints.exactly
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import ru.tinkoff.fintech.homework.lesson1.*

class HuntingTests : FeatureSpec() {
    val chicken = mockk<Chicken>()
    val hunter = mockk<Hunter>()

    init {

        val hunting = mockk<Hunting>()

        feature("Тестирование охоты") {

            scenario("Охотник не может убить, мёртвую курицу") {
                val hunter = Hunter(10, 10)
                every { hunting.hunter } returns hunter
                every { hunting.victim } returns chicken
                every { chicken.isAlive() } returns false
                every { hunting.successfulHunting() } returns false
                every { hunting.huntersShoot() } returns (hunting.hunter.isAlive() && hunting.victim.isAlive() && hunting.successfulHunting())

                hunting.huntersShoot() shouldBe false
            }

            scenario("Мёртвый охотник не может охотиться за курицей") {
                val chicken = Chicken(5, 3)
                every { hunting.hunter } returns hunter
                every { hunting.victim } returns chicken
                every { hunting.successfulHunting() } returns false
                every { hunting.huntersShoot() } returns (hunting.hunter.isAlive() && hunting.victim.isAlive() && hunting.successfulHunting())
                every { hunter.isAlive() } returns false

                hunting.huntersShoot() shouldBe false
            }

            scenario("При дефолтном расстоянии между животными, хищник может убить жертву с вероятностью 100%") {
                val chicken = Chicken(10, 22)
                val hunting = Hunting(hunter, chicken)
                every { hunter.isAlive() } returns true
                every { hunter.addEnergy(chicken.weight) } returns Unit

                hunting.huntersShoot() shouldBe true
            }
        }
    }
}
