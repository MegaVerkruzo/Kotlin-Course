//package ru.tinkoff.fintech.homework.lesson6.order
//
//import com.fasterxml.jackson.databind.ObjectMapper
//import com.fasterxml.jackson.module.kotlin.readValue
//import com.ninjasquad.springmockk.MockkBean
//import io.kotest.assertions.throwables.shouldThrow
//import io.kotest.core.spec.style.FeatureSpec
//import io.kotest.core.test.TestCase
//import io.kotest.core.test.TestResult
//import io.kotest.matchers.shouldBe
//import io.kotest.matchers.shouldNotBe
//import io.mockk.clearAllMocks
//import io.mockk.every
//import io.mockk.mockk
//import io.mockk.verify
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
//import org.springframework.boot.test.context.SpringBootTest
//import org.springframework.boot.test.mock.mockito.MockBean
//import org.springframework.http.HttpStatus
//import org.springframework.test.web.servlet.*
//import ru.tinkoff.fintech.homework.lesson6.common.StorageClient
//import ru.tinkoff.fintech.homework.lesson6.common.model.Cake
//import ru.tinkoff.fintech.homework.lesson6.common.model.Order
//import ru.tinkoff.fintech.homework.lesson6.storage.StorageController
//import ru.tinkoff.fintech.homework.lesson6.storage.StorageService
//import ru.tinkoff.fintech.homework.lesson6.storage.StorageDao
//import ru.tinkoff.fintech.homework.lesson6.store.OrderClient
//import ru.tinkoff.fintech.homework.lesson6.store.StoreController
//import java.nio.charset.StandardCharsets.UTF_8
//import io.kotest.core.extensions.Extension
//import io.kotest.extensions.spring.SpringExtension
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
//@AutoConfigureMockMvc
//class OrderTest(
//    private val mockMvc: MockMvc,
//    private val objectMapper: ObjectMapper,
//    private val orderClient: OrderClient,
//    private val storageClient: StorageClient,
//    private val storageService: StorageService,
//    private val orderService: OrderService,
//    private val storageController: StorageController,
//    private val storeController: StoreController,
//    private val orderController: OrderController
//) : FeatureSpec() {
//
//    @MockkBean
//    private lateinit var storageDao: StorageDao
//
//    @MockkBean
//    private lateinit var orderDao: OrderDao
//
//
//    private val orders: MutableMap<Int, Order> = mutableMapOf()
//    private var orderId: Int = 0
//
//    private val napoleon = Cake("napoleon", 623.5, 8)
//    private val medovik = Cake("medovik", 300.4, 3)
//    private val shokoladnie = Cake("Shokoladnie", 2405.4, 5)
//
//
//    private val data: MutableMap<String, Cake> = mutableMapOf()
//
//    override fun extensions(): List<Extension> = listOf(SpringExtension)
//
//    override fun beforeEach(testCase: TestCase) {
//        every { storageDao.getCakes() } returns data.values.toSet()
//        every { storageDao.getCake(any()) } answers { data[firstArg()] }
//        every { storageDao.updateCake(any()) } answers {
//            data[firstArg<Cake>().name] = firstArg()
//            firstArg()
//        }
//        every { orderDao.getOrder(any()) } answers { orders[firstArg()] }
//        every { orderDao.completedOrder(any()) } answers {
//            val order = orders[orderId]
//            requireNotNull(order) { "Нет такого заказа в базе!" }
//            val finishedOrder = order.copy(completed = true)
//            orders[orderId] = finishedOrder
//            finishedOrder
//        }
//        every { orderDao.addOrder(any()) } answers {
//            orders[++orderId] = firstArg<Order>().copy(id = orderId)
//            orderId
//        }
//    }
//
//    override fun afterEach(testCase: TestCase, result: TestResult) {
//        clearAllMocks()
//        data.clear()
//        orders.clear()
//        orderId = 0
//    }
//
//    init {
//        feature("Тестируем OrderService") {
//            scenario("Проверка работы методов store") {
//                getCakesStore()
//
//                verify(exactly = 1) { storageDao.getCakes() }
//            }
//            scenario("Проверяем, что можем получить тип торта") {
//
//                println(storageService.updateCake(napoleon.name, napoleon.cost, napoleon.count))
//                getCake("napoleon") shouldBe napoleon
//            }
//            scenario("Проверяем добавление заказа, на существующий тип торта") {
//
//                orderService.addOrder(napoleon.name, napoleon.count)
//                val order = orderService.getOrder(1)
//                order shouldNotBe null
//                order!!.cake shouldBe napoleon
//            }
//            scenario("Проверяем добавление несуществующего типа торта") {
//                shouldThrow<IllegalArgumentException> { orderService.addOrder("noElement", 4) }
//            }
//            scenario("Проверка выполнения корректного заказа") {
//                val initialCount = napoleon.count
//                val delta = 3
//                orderService.addOrder(napoleon.name, 3)
//                orderService.completedOrder(1)
//
//                val cake = storageService.getCake(napoleon.name)
//                cake shouldNotBe null
//                cake!!.count shouldBe initialCount - delta
//            }
//            scenario("Проверка выполнения некорректного заказа") {
//                orderService.addOrder(napoleon.name, 10)
//
//                shouldThrow<IllegalArgumentException> { orderService.completedOrder(0) }
//            }
//        }
//    }
//
//    private fun addOrder(name: String, count: Int): Order =
//        mockMvc.post("/order/add?name={name}&count={count}", name, count).readResponse()
//
//    private fun getOrder(orderId: Int): Order =
//        mockMvc.get("/order/{orderId}", orderId).readResponse()
//
//    private fun completedOrder(orderId: Int): Cake =
//        mockMvc.post("/order/{orderId}/complete", orderId).readResponse()
//
//    private fun getCakes(): Set<Cake> =
//        mockMvc.get("/storage/cake/list").readResponse()
//
//    private fun getCake(name: String): Cake =
//        mockMvc.get("/storage/cake?name={name}", name).readResponse()
//
//    private fun updateCake(name: String, cost: Double?, count: Int?): Cake =
//        mockMvc.patch("/storage/cake?name={name}&cost={cost}&count={count}", name, cost, count).readResponse()
//
//    private fun addCakesOrder(name: String, count: Int): Order =
//        mockMvc.post("/store/cake/add-order?name={name}&count={count}", name, count).readResponse()
//
//    private fun getCakesStore(): Set<Cake> =
//        mockMvc.get("/store/cake/list").readResponse()
//
//    private fun addCake(cake: Cake) {
//        mockMvc.put("/storage/cake?cake={cake}", cake)
//    }
//
//    private inline fun <reified T> ResultActionsDsl.readResponse(expectedStatus: HttpStatus = HttpStatus.OK): T = this
//        .andExpect { status { isEqualTo(expectedStatus.value()) } }
//        .andReturn().response.getContentAsString(UTF_8)
//        .let { if (T::class == String::class) it as T else objectMapper.readValue(it) }
//}
//
