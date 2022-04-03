package ru.tinkoff.fintech.homework.lesson6

import io.kotest.core.spec.style.FeatureSpec
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import ru.tinkoff.fintech.homework.lesson6.company.model.Cake
import ru.tinkoff.fintech.homework.lesson6.company.service.Storage
import ru.tinkoff.fintech.homework.lesson6.company.service.Store
import ru.tinkoff.fintech.homework.lesson6.company.service.client.CakeListClient
import ru.tinkoff.fintech.homework.lesson6.company.service.client.StorageClient

@SpringBootTest
@AutoConfigureMockMvc
class TestCakeStore : FeatureSpec() {

    @MockBean
    private val cakeListClient = mockk<CakeListClient>()

    @MockBean
    private val storageClient = mockk<StorageClient>()

    private val storage = Storage(storageClient)
    private val store = Store(cakeListClient, storage)

    override fun beforeEach(testCase: TestCase) {
        every { storageClient.getCakesWithAmount() } returns cakeList

    }

    override fun afterEach(testCase: TestCase, result: TestResult) {
        clearAllMocks()
    }

    init {
        feature("Тест класса Store") {
            scenario("Тест на вывод листа тортов") {
                store.getCakesList() shouldBe cakeList.map { it.key }
            }
        }
    }

    val cakeList: Map<Cake, Int> = mutableMapOf(
        Cake("Наполеон", 323.6) to 1,
        Cake("Рабыня", 494.0) to 3
    )
}

