package ru.tinkoff.fintech.homework.lesson6

import io.kotest.core.spec.style.FeatureSpec
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import ru.tinkoff.fintech.homework.lesson6.company.model.Cake
import ru.tinkoff.fintech.homework.lesson6.company.service.Storage
import ru.tinkoff.fintech.homework.lesson6.company.service.Store
import ru.tinkoff.fintech.homework.lesson6.company.service.client.StoreClient
import ru.tinkoff.fintech.homework.lesson6.company.service.client.StorageClient
import java.lang.IllegalArgumentException

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
        every { storageClient.getCakesList() } returns data
        every { storageClient.consistCake(any()) } answers { cakeCost.containsKey(firstArg<String>().toString()) && data[cakeCost[firstArg()]]!! > 0}
        every { storageClient.consistCakeType(any()) } answers { cakeCost.containsKey(firstArg()) }
        every { storageClient.changeCakesCount(any(), any()) } answers { data[cakeCost[firstArg()]!!] = secondArg<Int>().toString().toInt() + data[cakeCost[firstArg()]]!! }
        every { storageClient.addOrUpdateCake(any(), any()) } answers {
            var count = 0
            if (cakeCost.containsKey(firstArg())) {
                require(data.containsKey(cakeCost[firstArg()])) { throw IllegalArgumentException() }

                count = data[cakeCost[firstArg()]]!!
                data.remove(cakeCost[firstArg()]!!)
            }
            cakeCost.remove(firstArg())
            cakeCost[firstArg()] = Cake(firstArg(), secondArg<Double>().toString().toDouble())
            data[cakeCost[firstArg()]!!] = count
        }
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
                storage.consistCake("smth") shouldBe false
            }

            scenario("Тест на существования типа торта, но не имения его на складе") {
                data[firstCake] = 0

                storage.consistCakeType(firstCake.name) shouldBe true
                storage.consistCake(firstCake.name) shouldBe false
            }

            scenario("Проверка добавления кол-ва определённого вида торта") {
                val count = data[firstCake]!!

                storage.changeCakesCount(firstCake.name, 5)

                data[firstCake] shouldBe 5 + count
            }

            scenario("Пример добавления торта") {
                storage.addCakes(thirdCake.name, thirdCake.cost, 9)

                storage.consistCake(thirdCake.name) shouldBe true

                verify { storageClient.changeCakesCount(any(), any()) }
            }
        }
    }

    val cakeCost: MutableMap<String, Cake> = mutableMapOf(
        firstCake.name to firstCake,
        secondCake.name to secondCake
    )

    val data: MutableMap<Cake, Int> = mutableMapOf(
        firstCake to 1,
        secondCake to 3
    )
}

val firstCake = Cake("napoleon", 623.5)
val secondCake = Cake("medovik", 300.4)
val thirdCake = Cake("Shokoladnie", 2405.4)
