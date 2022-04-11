package ru.tinkoff.fintech.homework.lesson6.storage

import com.fasterxml.jackson.databind.ObjectMapper
import io.kotest.assertions.throwables.shouldThrow
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
import org.springframework.test.web.servlet.*
import ru.tinkoff.fintech.homework.lesson6.common.StorageClient
import ru.tinkoff.fintech.homework.lesson6.common.model.Cake
import ru.tinkoff.fintech.homework.lesson6.common.model.Order
import ru.tinkoff.fintech.homework.lesson6.order.OrderDao
import ru.tinkoff.fintech.homework.lesson6.order.OrderService
import ru.tinkoff.fintech.homework.lesson6.store.OrderClient

@SpringBootTest
@AutoConfigureMockMvc
class StorageTest(private val mockMvc: MockMvc, private val objectMapper: ObjectMapper) : FeatureSpec() {

    @MockBean
    private val storageDao = mockk<StorageDao>()

    @MockBean
    private val orderDao = mockk<OrderDao>()

    @MockBean
    private val orderClient = mockk<OrderClient>()

    @MockBean
    private val storageClient = mockk<StorageClient>()

    private val storageService = StorageService(storageDao)
    private val orderService = OrderService(orderDao, storageClient)

    override fun beforeEach(testCase: TestCase) {
        every { storageDao.getCakesList() } returns data.values.toSet()
        every { storageDao.getCake(any()) } answers { data[firstArg()]!! }
        every { storageDao.containCakes(any(), any()) } answers {
            data.containsKey(firstArg()) && storageDao.getCake(firstArg()).count >= secondArg<Int>()
        }
        every { storageDao.addNewCakeType(any()) } answers { data[firstArg<Cake>().name] = firstArg() }
        every { storageDao.updateCakesCount(any(), any()) } answers {
            data[firstArg()] =
                Cake(data[firstArg()]!!.name, data[firstArg()]!!.cost, data[firstArg()]!!.count + secondArg<Int>())
        }
        every { storageDao.updateCakesPrice(any(), any()) } answers {
            data[firstArg()] = Cake(data[firstArg()]!!.name, secondArg(), data[firstArg()]!!.count)
        }

        every { orderDao.getNumberOrder() } returns orders.size
        every { orderDao.getOrder(any()) } answers { orders[firstArg()] }
        every { orderDao.completeOrder(any()) } answers {
            orders[firstArg()] = Order(orders[firstArg()].orderId, orders[firstArg()].cake, true)
        }
        every { orderDao.addOrder(any(), any()) } answers {
            orders.add(
                Order(
                    orderDao.getNumberOrder(),
                    Cake(firstArg<Cake>().name, firstArg<Cake>().cost, secondArg()),
                    false
                )
            )
            orders.size - 1
        }

        every { orderClient.addCakesOrder(any(), any()) } answers {
            orderService.addOrder(firstArg(), secondArg())
        }
        every { storageClient.getCakesList() } answers { storageService.getCakesList() }
        every { storageClient.containCakeType(any()) } answers { storageService.containCakes(firstArg(), 0) }
        every { storageClient.getCake(any()) } answers { storageService.getCake(firstArg()) }
        every { storageClient.updateCakesParams(any(), any(), any()) } answers {
            storageService.updateCakesParams(
                firstArg(),
                secondArg(),
                thirdArg()
            )
        }
    }

    override fun afterEach(testCase: TestCase, result: TestResult) {
        clearAllMocks()
        data.clear()
        orders.clear()
        data[firstCake.name] = firstCake
        data[secondCake.name] = secondCake
    }

    init {
        feature("Тестируем StorageService") {
            scenario("Проверка получения корректную информацию о торте") {
                storageService.getCake(firstCake.name) shouldBe firstCake
            }
            scenario("Проверка получения информации о некорректном торте") {
                shouldThrow<IllegalArgumentException> { storageService.getCake("noElement") }
            }
            scenario("Проверка существования торта") {
                storageService.containCakes("noElement", 0) shouldBe false
                storageService.containCakes(firstCake.name, 0) shouldBe true
            }
            scenario("Проверка добавления тортов") {
                println(data[firstCake.name])
                storageService.addCakes(firstCake)
                println(data[firstCake.name])
                storageService.getCake(firstCake.name).count shouldBe firstCake.count * 2
            }
            scenario("Проверка обновлений параметров") {
                storageService.updateCakesParams(firstCake.name, null, 10)
                storageService.updateCakesParams(secondCake.name, 3.0, null)

                storageService.getCake(firstCake.name) shouldBe Cake(firstCake.name, firstCake.cost, firstCake.count + 10)
                storageService.getCake(secondCake.name) shouldBe Cake(secondCake.name, 3.0, secondCake.count)
            }

        }
    }

//    private fun addOrder(name: String, count: Int): Order =
//        mockMvc.post("/order/add?name={name}&count={count}", name, count).readResponse()
//
//    private fun getOrder(orderId: Int): Order =
//        mockMvc.get("/order/{orderId}", orderId).readResponse()
//
//    private fun completeOrder(orderId: Int): Cake =
//        mockMvc.post("/order/{orderId}/complete", orderId).readResponse()
//
//    private fun getCakesList(): Set<Cake> =
//        mockMvc.get("/storage/cake/list").readResponse()
//
//    private fun addCakesOrder(name: String, count: Int): Order =
//        mockMvc.post("/store/cake/add-order?name={name}&count={count}", name, count).readResponse()
//
//    private fun consistCakeType(name: String): Boolean =
//        mockMvc.get("/storage/cake/consist?name={name}", name).readResponse()
//
//    private fun getCakes(name: String): Cake =
//        mockMvc.get("/storage/cake/get?name={name}", name).readResponse()
//
//    private fun addCakes(cake: Cake) {
//        mockMvc.put("/storage/cake?cake={cake}", cake)
//    }
//
//    private fun updateCakeParams(name: String, cost: Double?, count: Int?): Cake =
//        mockMvc.patch("/storage/cake?name={name}&cost={cost}&count={count}", name, cost, count).readResponse()
//
//
//    private inline fun <reified T> ResultActionsDsl.readResponse(expectedStatus: HttpStatus = HttpStatus.OK): T = this
//        .andExpect { status { isEqualTo(expectedStatus.value()) } }
//        .andReturn().response.getContentAsString(UTF_8)
//        .let { if (T::class == String::class) it as T else objectMapper.readValue(it) }

    val orders: MutableList<Order> = mutableListOf()

    val firstCake = Cake("napoleon", 623.5, 8)
    val secondCake = Cake("medovik", 300.4, 3)
    val thirdCake = Cake("Shokoladnie", 2405.4, 5)


    val data: MutableMap<String, Cake> = mutableMapOf(
        firstCake.name to firstCake,
        secondCake.name to secondCake
    )
}

