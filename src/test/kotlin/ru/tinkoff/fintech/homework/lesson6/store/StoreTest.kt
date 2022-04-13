package ru.tinkoff.fintech.homework.lesson6.order

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.spec.style.FeatureSpec
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.verify
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.test.web.servlet.*
import ru.tinkoff.fintech.homework.lesson6.common.StorageClient
import ru.tinkoff.fintech.homework.lesson6.common.model.Cake
import ru.tinkoff.fintech.homework.lesson6.common.model.Order
import ru.tinkoff.fintech.homework.lesson6.storage.StorageController
import ru.tinkoff.fintech.homework.lesson6.storage.StorageService
import ru.tinkoff.fintech.homework.lesson6.storage.StorageDao
import ru.tinkoff.fintech.homework.lesson6.store.OrderClient
import ru.tinkoff.fintech.homework.lesson6.store.StoreController
import java.nio.charset.StandardCharsets.UTF_8
import io.kotest.core.extensions.Extension
import io.kotest.extensions.spring.SpringExtension

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
class StoreTest(
    private val mockMvc: MockMvc,
    private val objectMapper: ObjectMapper,
    private val orderClient: OrderClient,
    private val storageClient: StorageClient,
    private val storageService: StorageService,
    private val orderService: OrderService,
    private val storageController: StorageController,
    private val storeController: StoreController,
    private val orderController: OrderController
) : FeatureSpec() {

    @MockkBean
    private lateinit var storageDao: StorageDao

    @MockkBean
    private lateinit var orderDao: OrderDao


    private val orders: MutableMap<Int, Order> = mutableMapOf()
    private var orderId: Int = 0

    private val napoleon = Cake("napoleon", 623.5, 8)
    private val medovik = Cake("medovik", 300.4, 3)
    private val shokoladnie = Cake("Shokoladnie", 2405.4, 5)


    private val data: MutableMap<String, Cake> = mutableMapOf()

    override fun extensions(): List<Extension> = listOf(SpringExtension)

    override fun beforeEach(testCase: TestCase) {
        every { storageDao.getCakes() } answers { data.values.toSet() }
        every { storageDao.getCake(any()) } answers { data[firstArg()] }
        every { storageDao.updateCake(any()) } answers {
            data[firstArg<Cake>().name] = firstArg()
            firstArg()
        }
        every { orderDao.getOrder(any()) } answers { orders[firstArg()] }
        every { orderDao.completedOrder(any()) } answers {
            val order = orders[orderId]
            requireNotNull(order) { "Нет такого заказа в базе!" }
            val finishedOrder = order.copy(completed = true)
            orders[orderId] = finishedOrder
            finishedOrder
        }
        every { orderDao.addOrder(any()) } answers {
            orders[++orderId] = firstArg<Order>().copy(id = orderId)
            orderId
        }
    }

    override fun afterEach(testCase: TestCase, result: TestResult) {
        clearAllMocks()
        data.clear()
        orders.clear()
        orderId = 0
    }

    init {
        feature("Тестируем StoreController") {
            scenario("Проверка добавления заказа на несколько тортов") {
                updateCake(napoleon.name, napoleon.cost, napoleon.count) shouldBe napoleon
                updateCake(medovik.name, medovik.cost, medovik.count) shouldBe medovik
                addOrderStore(napoleon.name, 3) shouldBe 1
                getOrder(1)!!.cake shouldBe napoleon.copy(count = 3)
                verify(exactly = 3) { storageDao.getCake(any()) }
                verify(exactly = 1) { orderDao.addOrder(any()) }
                verify(exactly = 1) { orderDao.getOrder(any()) }
            }
        }
    }

    private fun addOrder(name: String, count: Int): Int =
        mockMvc.post("/order/add?name={name}&count={count}", name, count).readResponse()

    private fun getOrder(orderId: Int): Order? =
        mockMvc.get("/order/{orderId}", orderId).readResponse()

    private fun completedOrder(orderId: Int): Order =
        mockMvc.post("/order/{orderId}/complete", orderId).readResponse()

    private fun getCakes(): Set<Cake> =
        mockMvc.get("/storage/cake/list").readResponse()

    private fun getCake(name: String): Cake? =
        mockMvc.get("/storage/cake?name={name}", name).readResponse()

    private fun updateCake(name: String, cost: Double?, count: Int?): Cake =
        mockMvc.patch("/storage/cake?name={name}&cost={cost}&count={count}", name, cost, count).readResponse()

    private fun addOrderStore(name: String, count: Int): Int =
        mockMvc.post("/store/cake/add-order?name={name}&count={count}", name, count).readResponse()

    private fun getCakesStore(): Set<Cake> =
        mockMvc.get("/store/cake/list").readResponse()

    private fun addCake(cake: Cake) {
        mockMvc.put("/storage/cake?cake={cake}", cake)
    }

    private inline fun <reified T> ResultActionsDsl.readResponse(expectedStatus: HttpStatus = HttpStatus.OK): T = this
        .andExpect { status { isEqualTo(expectedStatus.value()) } }
        .andReturn().response.getContentAsString(UTF_8)
        .let { if (T::class == String::class) it as T else objectMapper.readValue(it) }
}

