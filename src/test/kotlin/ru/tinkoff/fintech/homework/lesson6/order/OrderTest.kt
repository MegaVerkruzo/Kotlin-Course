package ru.tinkoff.fintech.homework.lesson6.order

import com.fasterxml.jackson.databind.ObjectMapper
import io.kotest.matchers.shouldBe
import io.mockk.verify
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.*
import io.kotest.matchers.collections.shouldContainAll
import ru.tinkoff.fintech.homework.lesson6.TestUtils
import ru.tinkoff.fintech.homework.lesson6.medovik
import ru.tinkoff.fintech.homework.lesson6.napoleon

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
class OrderTest(private val mockMvc: MockMvc, private val objectMapper: ObjectMapper) : TestUtils(mockMvc, objectMapper) {

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
    
}