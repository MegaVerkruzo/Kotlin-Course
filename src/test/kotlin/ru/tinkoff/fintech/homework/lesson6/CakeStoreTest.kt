package ru.tinkoff.fintech.homework.lesson6

import io.kotest.assertions.throwables.shouldThrow
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
import ru.tinkoff.fintech.homework.lesson6.company.model.Order
import ru.tinkoff.fintech.homework.lesson6.company.storage.Storage
import ru.tinkoff.fintech.homework.lesson6.company.store.Store
import ru.tinkoff.fintech.homework.lesson6.company.storage.StorageClient
import ru.tinkoff.fintech.homework.lesson6.company.store.StoreClient
import java.lang.IllegalArgumentException

@SpringBootTest
@AutoConfigureMockMvc
class CakeStoreTest : FeatureSpec() {

    @MockBean
    private val storeClient = mockk<StoreClient>()

    @MockBean
    private val storageClient = mockk<StorageClient>()

    private val slot = slot<Int>()

    private val storage = Storage(storageClient)
    private val store = Store(storeClient)

    override fun beforeEach(testCase: TestCase) {
        every { storageClient.getCakesList() } returns data
        every { storageClient.getCakeCount(any()) } answers { data[firstArg()]!!.second }
        every { storageClient.getCakeCost(any()) } answers { data[firstArg()]!!.first.cost }
        every {
            storageClient.consistCakes(
                any(),
                any()
            )
        } answers { data.containsKey(firstArg()) && storageClient.getCakeCount(firstArg()) >= secondArg<Int>() }
        every { storageClient.addNewCakeType(any(), any(), any()) } answers {
            data[firstArg()] = Pair(Cake(firstArg(), secondArg()), thirdArg())
        }
        every { storageClient.addCakesCount(any(), any()) } answers {
            data[firstArg()] = Pair(data[firstArg()]!!.first, storageClient.getCakeCount(firstArg()) + secondArg<Int>())
        }
        every { storageClient.changeCakePrice(any(), any()) } answers {
            data[firstArg()] = Pair(Cake(firstArg(), secondArg()), storageClient.getCakeCount(firstArg()))
        }
        every { storageClient.getNumberOrder() } returns orders.size
        every { storageClient.getOrder(slot) } returns orders[slot.captured]
        every { storageClient.doneOrder(any()) } answers {
            orders[firstArg()].packed = true
        }
//        every { storageClient.addOrder(any(), any()) } answers {
//            orders.add(Order(orders.size, firstArg(), secondArg(), false))
//
//        }
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

                verify(exactly = 1) { storageClient.changeCakesCount(any(), any()) }
            }
        }

        feature("Тест свзяки класса Storage и Store") {
            scenario("Удачный отвоз тортов из склада в магазин") {
                store.buyCakes(firstCake.name, 1)

                storage.doneOrder(0)

                orders[0].packed shouldBe true
            }

            scenario("Не хватает тортов для выполнения заказа") {
                shouldThrow<IllegalArgumentException> { store.buyCakes(firstCake.name, 654) }
            }
        }
    }

    val orders: MutableList<Order> = mutableListOf()

    val data: MutableMap<String, Pair<Cake, Int>> = mutableMapOf(
        firstCake.name to Pair(firstCake, 1),
        secondCake.name to Pair(secondCake, 3)
    )
}

val firstCake = Cake("napoleon", 623.5)
val secondCake = Cake("medovik", 300.4)
val thirdCake = Cake("Shokoladnie", 2405.4)
