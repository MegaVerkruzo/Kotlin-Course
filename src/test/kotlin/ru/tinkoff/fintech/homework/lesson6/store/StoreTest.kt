package ru.tinkoff.fintech.homework.lesson6.store

import com.fasterxml.jackson.databind.ObjectMapper
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.shouldBe
import io.mockk.verify
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.test.web.servlet.*
import ru.tinkoff.fintech.homework.lesson6.TestUtils
import ru.tinkoff.fintech.homework.lesson6.medovik
import ru.tinkoff.fintech.homework.lesson6.napoleon
import ru.tinkoff.fintech.homework.lesson6.shokoladnie

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
class StoreTest(private val mockMvc: MockMvc, private val objectMapper: ObjectMapper) : TestUtils(mockMvc, objectMapper) {
    init {
        feature("Тестируем StoreController") {
            scenario("Проверка добавления корректного заказа на несколько тортов") {
                updateCake(napoleon.name, napoleon.cost, napoleon.count)
                updateCake(medovik.name, medovik.cost, medovik.count)
                val orderId = addOrderStore(napoleon.name, 3)

                getOrder(orderId)!!.cake shouldBe napoleon.copy(count = 3)
                verify(exactly = 3) { storageDao.getCake(any()) }
                verify(exactly = 1) { orderDao.addOrder(any()) }
                verify(exactly = 1) { orderDao.getOrder(any()) }
            }
            scenario("Проверка добавления некорректного заказа на несуществующий торт") {
                shouldThrow<Exception> { addOrder(napoleon.name, 10, HttpStatus.BAD_REQUEST) }
            }
            scenario("Проверка вывода списка тортов") {
                updateCake(napoleon.name, napoleon.cost, napoleon.count)
                updateCake(medovik.name, medovik.cost, medovik.count)
                updateCake(shokoladnie.name, shokoladnie.cost, shokoladnie.count)

                getCakesStore() shouldContainAll setOf(napoleon, medovik, shokoladnie)
            }
        }
    }
}

