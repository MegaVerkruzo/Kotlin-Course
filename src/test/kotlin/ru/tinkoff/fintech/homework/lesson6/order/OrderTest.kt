package ru.tinkoff.fintech.homework.lesson6.order

import com.fasterxml.jackson.databind.ObjectMapper
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
import org.springframework.test.web.servlet.MockMvc
import ru.tinkoff.fintech.homework.lesson6.common.StorageClient
import ru.tinkoff.fintech.homework.lesson6.model.Cake
import ru.tinkoff.fintech.homework.lesson6.model.Order
import ru.tinkoff.fintech.homework.lesson6.storage.StorageService
import ru.tinkoff.fintech.homework.lesson6.storage.StorageDao
import ru.tinkoff.fintech.homework.lesson6.store.OrderClient

@SpringBootTest
@AutoConfigureMockMvc
class OrderTest(private val mockMvc: MockMvc, private val objectMapper: ObjectMapper) : FeatureSpec() {

    @MockBean
    private val storageDao = mockk<StorageDao>()

    @MockBean
    private val orderDao = mockk<OrderDao>()

    @MockBean
    private lateinit var orderClient: OrderClient

    @MockBean
    private lateinit var storageClient: StorageClient

    private val storageService = StorageService(storageDao)
    private val orderService = OrderService(orderDao, storageClient)

    override fun beforeEach(testCase: TestCase) {
        every { storageDao.getCakesList() } returns data.values.toSet()
        every { storageDao.getCake(any()) } answers { data[firstArg()]!! }
        every { storageDao.consistCakes(any(), any()) } answers {
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
            orders.add(Order(orderDao.getNumberOrder(), Cake(firstArg<Cake>().name, firstArg<Cake>().cost, secondArg()), false))
            orders.size - 1
        }
    }

    override fun afterEach(testCase: TestCase, result: TestResult) {
        clearAllMocks()
    }

    init {

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

