package ru.tinkoff.fintech.homework.lesson6.order

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
import ru.tinkoff.fintech.homework.lesson6.storage.StorageService
import ru.tinkoff.fintech.homework.lesson6.storage.StorageDao
import ru.tinkoff.fintech.homework.lesson6.store.OrderClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
class OrderTest(private val mockMvc: MockMvc, private val objectMapper: ObjectMapper) : FeatureSpec() {

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
        every { storageDao.getCakes() } returns data.values.toSet()
        every { storageDao.getCake(any()) } answers { data[firstArg()] }
        every { storageDao.updateCake(any()) } answers { data[firstArg<Cake>().name] = firstArg() }

        every { orderDao.getOrder(any()) } answers { orders[firstArg()] }
        every { orderDao.completeOrder(any()) } answers {
            val order: Order? = orderDao.getOrder(firstArg())
            requireNotNull(order) { "Нет такого заказа в базе!" }
            orders[firstArg()] = order.copy(completed = true)
        }
        every { orderDao.addOrder(any()) } answers {
            orders[orders.size + 1] = firstArg()
            orders.size
        }

        every { orderClient.addCakeOrder(any(), any()) } answers {
            orderService.addOrder(firstArg(), secondArg())
        }
        every { storageClient.getCakes() } answers { storageService.getCakes() }
        every { storageClient.getCake(any()) } answers { storageService.getCake(firstArg()) }
        every { storageClient.updateCake(any(), any(), any()) } answers {
            storageService.updateCake(
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
        feature("Тестируем OrderService") {
            scenario("Проверяем добавление заказа, на существующий тип торта") {
                orderService.addOrder(firstCake.name, firstCake.count)

                orderService.getOrder(0).cake shouldBe firstCake
            }
            scenario("Проверяем добавление несуществующего типа торта") {
                shouldThrow<IllegalArgumentException> { orderService.addOrder("noElement", 4) }
            }
            scenario("Проверка выполнения корректного заказа") {
                val initialCount = firstCake.count
                val delta = 3
                orderService.addOrder(firstCake.name, 3)
                orderService.completeOrder(0)

                storageService.getCake(firstCake.name).count shouldBe initialCount - delta
            }
            scenario("Проверка выполнения некорректного заказа") {
                orderService.addOrder(firstCake.name, 10)

                shouldThrow<IllegalArgumentException> { orderService.completeOrder(0) }
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

    val orders: MutableMap<Int, Order> = mutableMapOf()

    val firstCake = Cake("napoleon", 623.5, 8)
    val secondCake = Cake("medovik", 300.4, 3)
    val thirdCake = Cake("Shokoladnie", 2405.4, 5)


    val data: MutableMap<String, Cake> = mutableMapOf(
        firstCake.name to firstCake,
        secondCake.name to secondCake
    )
}

