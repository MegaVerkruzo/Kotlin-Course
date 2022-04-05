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
import ru.tinkoff.fintech.homework.lesson6.company.service.client.StoreClient
import ru.tinkoff.fintech.homework.lesson6.company.service.client.StorageClient

@SpringBootTest
@AutoConfigureMockMvc
class TestCakeStore : FeatureSpec() {

    @MockBean
    private val storeClient = mockk<StoreClient>()

    @MockBean
    private val storageClient = mockk<StorageClient>()

    private val storage = Storage(storageClient)
    private val store = Store(storeClient)

    override fun beforeEach(testCase: TestCase) {
        every { storageClient.getCakesList() } returns cakeList
//        every { storageClient.consistCakeType(any()) } answers { cakeList.get(storage)}
    }

    override fun afterEach(testCase: TestCase, result: TestResult) {
        clearAllMocks()
    }

    init {
        feature("Тест класса Storage") {
            scenario("Тест на проверку существования товара") {
                storage.consistCake(firstCake.name) shouldBe true
                storage.consistCake(secondCake.name) shouldBe true
                storage.consistCake("morgensternCake") shouldBe false
            }
        }
    }

    val cakeList: MutableMap<Cake, Int> = mutableMapOf(
        firstCake to 1,
        secondCake to 3
    )


}

val firstCake = Cake("napoleon", 623.5)
val secondCake = Cake("medovik", 300.4)

