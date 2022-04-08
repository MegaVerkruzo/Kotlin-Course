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
import ru.tinkoff.fintech.homework.lesson6.model.Cake
import ru.tinkoff.fintech.homework.lesson6.model.Order
import ru.tinkoff.fintech.homework.lesson6.storage.StorageService
import ru.tinkoff.fintech.homework.lesson6.storage.StorageDao
import ru.tinkoff.fintech.homework.lesson6.store.StoreClient

@SpringBootTest
@AutoConfigureMockMvc
class CakeStoreTest : FeatureSpec() {

    @MockBean
    private val storeClient = mockk<StoreClient>()

    @MockBean
    private val storageDao = mockk<StorageDao>()

    private val storageService = StorageService(storageDao)

    override fun beforeEach(testCase: TestCase) {
        every { storageDao.getCakes() } returns data.values
        every { storageDao.getCakeCount(any()) } answers { data[firstArg()]!!.count }
        every { storageDao.getCakeCost(any()) } answers { data[firstArg()]!!.cost }
        every {
            storageDao.consistCakes(
                any(),
                any()
            )
        } answers { data.containsKey(firstArg()) && storageDao.getCakeCount(firstArg()) >= secondArg<Int>() }
        every { storageDao.addNewCakeType(any()) } answers { data[firstArg<Cake>().name] = firstArg() }
        every { storageDao.updateCakesCount(any(), any()) } answers {
            val initialValue = data[firstArg()]!!
            data[firstArg()] = Cake(initialValue.name, initialValue.cost, initialValue.count + secondArg<Int>())
        }
        every { storageDao.updateCakesPrice(any(), any()) } answers {
            data[firstArg()] = Cake(firstArg(), secondArg(), storageDao.getCakeCount(firstArg()))
        }
        every { storageDao.getNumberOrder() } returns orders.size
        every { storageDao.getOrder(any()) } answers { orders[firstArg()] }
        every { storageDao.completeOrder(any()) } answers {
            val initialValue = orders[firstArg()]
            orders[firstArg()] = Order(initialValue.orderId, initialValue.cake, true)
        }
        every { storageDao.addOrder(any(), any()) } answers {
            orders.add(Order(orders.size, Cake(firstArg<Cake>().name, firstArg<Cake>().cost, secondArg()), false))
            orders.size - 1
        }
        every { storeClient.getCakesList() } returns data.map { it -> it.value }
        every { storeClient.buyCakes(any(), any()) } answers {
            orders.add(Order(orders.size, Cake(firstArg<Cake>().name, firstArg<Cake>().cost, secondArg()), false))
            orders.last()
        }
    }

    override fun afterEach(testCase: TestCase, result: TestResult) {
        clearAllMocks()
    }

    init {
        feature("Тест класса Storage") {
            scenario("Тест на проверку существования товара") {
                storageService.consistCakeType(firstCake.name) shouldBe true
                storageService.consistCakeType(secondCake.name) shouldBe true
                storageService.consistCakeType("morgensternCake") shouldBe false
                storageService.consistCakeType("smth") shouldBe false
            }

            scenario("Тест на существования типа торта, но не имения его на складе") {
                storageService.updateCakeParams(
                    firstCake.name, null, -storageService.getCakesCount(firstCake.name)
                )

                storageService.consistCakeType(firstCake.name) shouldBe true
                storageService.consistCakes(firstCake.name, 1) shouldBe false
            }

            scenario("Проверка добавления кол-ва определённого вида торта") {
                val count = storageService.getCakesCount(firstCake.name)

                storageService.updateCakeParams(firstCake.name, null, 5)

                storageService.getCakesCount(firstCake.name) shouldBe 5 + count
            }

            scenario("Пример добавления торта") {
                storageService.updateCakeParams(thirdCake.name, thirdCake.cost, thirdCake.count)

                storageService.consistCakeType(thirdCake.name) shouldBe true

                verify(exactly = 1) { storageDao.addNewCakeType(any()) }
            }
        }
    }

    val orders: MutableList<Order> = mutableListOf()

    val firstCake = Cake("napoleon", 623.5, 1)
    val secondCake = Cake("medovik", 300.4, 3)
    val thirdCake = Cake("Shokoladnie", 2405.4, 5)


    val data: MutableMap<String, Cake> = mutableMapOf(
        firstCake.name to firstCake,
        secondCake.name to secondCake
    )
}

