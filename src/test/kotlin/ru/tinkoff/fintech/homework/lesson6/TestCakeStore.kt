package ru.tinkoff.fintech.homework.lesson6

import io.kotest.core.spec.style.FeatureSpec
import io.kotest.core.test.TestCase
import io.mockk.MockK
import io.mockk.every
import ru.tinkoff.fintech.homework.lesson6.company.service.client.CakeListClient

class TestCakeStore : FeatureSpec(){

    val cakelistClient = MockK<CakeListClient>

    override fun beforeEach(testCase: TestCase) {
        every {  }
    }

    init {
        feature("Тест класса Store") {
            scenario("Тест на нахождение торта в списке") {

            }
        }
    }
}