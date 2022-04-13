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

    val orders: MutableMap<Int, Order> = mutableMapOf()
    var orderId: Int = 0

    val data: MutableMap<String, Cake> = mutableMapOf()

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

    protected fun addOrder(name: String, count: Int, expectedStatus: HttpStatus = HttpStatus.OK): Int =
        mockMvc.post("/order/add?name={name}&count={count}", name, count).readResponse(expectedStatus)

    protected fun getOrder(orderId: Int, expectedStatus: HttpStatus = HttpStatus.OK): Order? =
        mockMvc.get("/order/{orderId}", orderId).readResponse(expectedStatus)

    protected fun completedOrder(orderId: Int, expectedStatus: HttpStatus = HttpStatus.OK): Order =
        mockMvc.post("/order/{orderId}/complete", orderId).readResponse(expectedStatus)

    protected fun getCakes(): Set<Cake> =
        mockMvc.get("/storage/cake/list").readResponse()

    protected fun getCake(name: String): Cake? =
        mockMvc.get("/storage/cake?name={name}", name).readResponse()

    protected fun updateCake(
        name: String,
        cost: Double?,
        count: Int?,
        expectedStatus: HttpStatus = HttpStatus.OK
    ): Cake =
        mockMvc.patch("/storage/cake?name={name}&cost={cost}&count={count}", name, cost, count)
            .readResponse(expectedStatus)

    protected fun getCakesStore(): Set<Cake> =
        mockMvc.get("/store/cake/list").readResponse()

    protected fun addOrderStore(name: String, count: Int, expectedStatus: HttpStatus = HttpStatus.OK): Int =
        mockMvc.post("/store/cake/add-order?name={name}&count={count}", name, count).readResponse(expectedStatus)

    private inline fun <reified T> ResultActionsDsl.readResponse(expectedStatus: HttpStatus = HttpStatus.OK): T = this
        .andExpect { status { isEqualTo(expectedStatus.value()) } }
        .andReturn().response.getContentAsString(UTF_8)
        .let { if (T::class == String::class) it as T else objectMapper.readValue(it) }
}

val napoleon = Cake("napoleon", 623.5, 8)
val medovik = Cake("medovik", 300.4, 3)
val shokoladnie = Cake("Shokoladnie", 2405.4, 5)
