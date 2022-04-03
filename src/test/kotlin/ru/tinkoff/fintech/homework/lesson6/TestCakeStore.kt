package ru.tinkoff.fintech.homework.lesson6

import io.kotest.core.spec.style.FeatureSpec
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldContainAll
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import ru.tinkoff.fintech.homework.lesson6.company.model.Cake
import ru.tinkoff.fintech.homework.lesson6.company.service.Storage
import ru.tinkoff.fintech.homework.lesson6.company.service.Store
import ru.tinkoff.fintech.homework.lesson6.company.service.client.CakeListClient
import ru.tinkoff.fintech.homework.lesson6.company.service.client.FeedbackListClient

@SpringBootTest
@AutoConfigureMockMvc
class TestCakeStore : FeatureSpec(){

    @MockBean
    private lateinit var cakeListClient: CakeListClient

    @MockBean
    private lateinit var feedBackListClient: FeedbackListClient

    @MockBean
    private lateinit var storage: Storage

    override fun beforeEach(testCase: TestCase) {
        every { cakeListClient.getCakesList() } returns cakeList

    }

    override fun afterEach(testCase: TestCase, result: TestResult) {
        clearAllMocks()
    }

    init {
        feature("Тест класса Store") {
            scenario("Тест на вывод листа тортов") {
                val cakeList = Store(cakeListClient, feedBackListClient, storage)

                cakeList.getCakesList().shouldContainAll(cakeList)
            }
        }
    }

    val cakeList: Set<Cake> = setOf(
        Cake(1, "Наполеон", 323.6),
        Cake(2, "Рабыня", 494.0)
    )
}