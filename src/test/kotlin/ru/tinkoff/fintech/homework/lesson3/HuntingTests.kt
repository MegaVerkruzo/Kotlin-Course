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
    val hunting = Hunting(hunter, chicken)

    override fun beforeEach(testCase: TestCase) {
        mockkStatic(::nextDouble)

        every { chicken.isAlive() } returns true
        every { chicken.weight } returns 0
        every { hunter.isAlive() } returns true
        justRun { chicken.die() }
        justRun { chicken.run() }
        justRun { hunter.run() }
        justRun { hunter.addEnergy(any()) }
    }

    override fun afterEach(testCase: TestCase, result: TestResult) {
        unmockkStatic(::nextDouble)
        clearAllMocks()
    }

    init {
        feature("Тестирование охоты") {
            scenario("Охотник не может убить, мёртвую курицу") {
                every { nextDouble() } returns 0.0
                every { chicken.isAlive() } returns false

                hunting.huntersShoot() shouldBe false
            }

            scenario("Мёртвый охотник не может охотиться за курицей") {
                every { nextDouble() } returns 0.0
                every { hunter.isAlive() } returns false

                hunting.huntersShoot() shouldBe false
            }

            scenario("Охотник имеет sudo оружие, которое убивает живую жертву") {
                every { nextDouble() } returns 0.0

                hunting.huntersShoot() shouldBe true

                verify(exactly = 1) { nextDouble() }
            }

            scenario("Охотнику повезло попасть с шансом на попадание 0.333..") {
                every { nextDouble() } returns 0.25

                hunting.victimsRun()
                hunting.victimsRun()
                hunting.huntersShoot() shouldBe true

                verify(exactly = 1) {  nextDouble() }
                verify(exactly = 2) { chicken.run() }
            }

            scenario("Охотнику не повезло попасть с шансом на попадание 0.333..") {
                every { nextDouble() } returns 0.5

                hunting.victimsRun()
                hunting.victimsRun()
                hunting.huntersShoot() shouldBe false

                verify(exactly = 1) { nextDouble() }
                verify(exactly = 2) { chicken.run() }
            }
        }
    }
}
