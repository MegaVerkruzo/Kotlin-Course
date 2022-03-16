package ru.tinkoff.fintech.homework.lesson3

import io.kotest.core.spec.BeforeSpec
import io.kotest.core.spec.Spec
import io.kotest.core.spec.style.FeatureSpec
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.kotest.matchers.ints.exactly
import io.kotest.matchers.shouldBe
import io.mockk.*
import ru.tinkoff.fintech.homework.lesson1.*

class HuntingTests : FeatureSpec() {
    val chicken = mockk<Chicken>()
    val hunter = mockk<Hunter>()

    override fun beforeEach(testCase: TestCase) {
        mockkStatic(::nextDouble)
        mockkStatic(::successfulHunting)
        every { hunter.isAlive() } returns true
        every { chicken.isAlive() } returns true
        justRun { chicken.die() }
        justRun { chicken.run() }
        justRun { hunter.run() }
        every { chicken.weight } returns 0
        justRun { hunter.addEnergy(any()) }
    }

    override fun afterEach(testCase: TestCase, result: TestResult) {
        unmockkStatic(::nextDouble)
        unmockkStatic(::successfulHunting)
        clearAllMocks()
    }



    init {
        feature("Тестирование охоты") {
            scenario("Охотник не может убить, мёртвую курицу") {
                val hunting = Hunting(hunter, chicken)
                every { successfulHunting(hunting) } returns true
                every { chicken.isAlive() } returns false

                hunting.huntersShoot() shouldBe false
            }

            scenario("Мёртвый охотник не может охотиться за курицей") {
                val hunting = Hunting(hunter, chicken)
                every { successfulHunting(hunting) } returns true
                every { hunter.isAlive() } returns false

                hunting.huntersShoot() shouldBe false
            }

            scenario("Охотник имеет sudo оружие, которое убивает живую жертву") {
                val hunting = Hunting(hunter, chicken)
                every { successfulHunting(hunting) } returns true

                hunting.huntersShoot() shouldBe true

                verify (exactly = 1) { successfulHunting(hunting) }
            }

            scenario("Охотнику повезло попасть с шансом на попадание 0.333..") {
                val hunting = Hunting(hunter, chicken)
                every { nextDouble() } returns 0.25

                hunting.victimsRun()
                hunting.victimsRun()
                hunting.huntersShoot() shouldBe true

                verify (exactly = 1) { successfulHunting(any()) }
                verify (exactly = 2) { chicken.run() }
            }

            scenario("Охотнику не повезло попасть с шансом на попадание 0.333..") {
                val hunting = Hunting(hunter, chicken)
                every { nextDouble() } returns 0.5

                hunting.victimsRun()
                hunting.victimsRun()
                hunting.huntersShoot() shouldBe false

                verify (exactly = 1) { successfulHunting(any()) }
                verify (exactly = 2) { chicken.run() }
            }
        }
    }
}
