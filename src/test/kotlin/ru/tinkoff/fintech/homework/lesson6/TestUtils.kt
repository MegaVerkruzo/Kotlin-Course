package ru.tinkoff.fintech.homework.lesson6

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
import ru.tinkoff.fintech.homework.lesson6.common.model.Cake
import ru.tinkoff.fintech.homework.lesson6.common.model.Order
import ru.tinkoff.fintech.homework.lesson6.storage.StorageDao
import java.nio.charset.StandardCharsets.UTF_8
import io.kotest.core.extensions.Extension
import io.kotest.extensions.spring.SpringExtension
import io.kotest.matchers.collections.shouldContainAll
import ru.tinkoff.fintech.homework.lesson6.order.OrderDao

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
class TestUtils(private val mockMvc: MockMvc, private val objectMapper: ObjectMapper) : FeatureSpec() {

    @MockkBean
    protected lateinit var storageDao: StorageDao

    @MockkBean
    protected lateinit var orderDao: OrderDao

    protected val orders: MutableMap<Int, Order> = mutableMapOf()
    protected var orderId: Int = 0

    protected val data: MutableMap<String, Cake> = mutableMapOf()

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
        feature("Тестируем OrderController") {
            scenario("Проверка добавления заказа") {
                updateCake(napoleon.name, napoleon.cost, napoleon.count)
                val orderId = addOrder(napoleon.name, 3)

                orderId shouldBe 1
                getOrder(orderId)!!.cake shouldBe napoleon.copy(count = 3)
                verify(exactly = 1) { orderDao.addOrder(any()) }
                verify(exactly = 1) { orderDao.getOrder(any()) }
                verify(exactly = 2) { storageDao.getCake(any()) }
            }
            scenario("Проверка выполнения заказа") {
                updateCake(napoleon.name, napoleon.cost, napoleon.count)
                val orderId = addOrder(napoleon.name, 3)
                completedOrder(orderId).completed shouldBe true
            }
            scenario("Проверка на set") {
                updateCake(napoleon.name, napoleon.cost, napoleon.count)
                updateCake(medovik.name, medovik.cost, medovik.count)
                getCakes() shouldContainAll setOf(napoleon, medovik)
            }
        }
    }

    protected fun addOrder(name: String, count: Int): Int =
        mockMvc.post("/order/add?name={name}&count={count}", name, count).readResponse()

    protected fun getCakes(): Set<Cake> =
        mockMvc.get("/storage/cake/list").readResponse()

    protected fun getOrder(orderId: Int): Order? =
        mockMvc.get("/order/{orderId}", orderId).readResponse()

    protected fun completedOrder(orderId: Int): Order =
        mockMvc.post("/order/{orderId}/complete", orderId).readResponse()

    protected fun updateCake(name: String, cost: Double?, count: Int?): Cake =
        mockMvc.patch("/storage/cake?name={name}&cost={cost}&count={count}", name, cost, count).readResponse()

    protected fun getCake(name: String): Cake? =
        mockMvc.get("/storage/cake?name={name}", name).readResponse()

    protected fun addOrderStore(name: String, count: Int): Int =
        mockMvc.post("/store/cake/add-order?name={name}&count={count}", name, count).readResponse()

    private inline fun <reified T> ResultActionsDsl.readResponse(expectedStatus: HttpStatus = HttpStatus.OK): T = this
        .andExpect { status { isEqualTo(expectedStatus.value()) } }
        .andReturn().response.getContentAsString(UTF_8)
        .let { if (T::class == String::class) it as T else objectMapper.readValue(it) }
}

val napoleon = Cake("napoleon", 623.5, 8)
val medovik = Cake("medovik", 300.4, 3)
val shokoladnie = Cake("Shokoladnie", 2405.4, 5)
