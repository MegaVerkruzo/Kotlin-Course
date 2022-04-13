package ru.tinkoff.fintech.homework.lesson6.order

import com.fasterxml.jackson.databind.ObjectMapper
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.mockk.verify
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.*
import org.springframework.http.HttpStatus
import ru.tinkoff.fintech.homework.lesson6.TestUtils
import ru.tinkoff.fintech.homework.lesson6.napoleon

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
class OrderTest(private val mockMvc: MockMvc, private val objectMapper: ObjectMapper) :
    TestUtils(mockMvc, objectMapper) {
    init {
        feature("Тестируем OrderController") {
            scenario("Проверка добавления 1-ого корректного заказа") {
                updateCake(napoleon.name, napoleon.cost, napoleon.count)

                val orderId = addOrder(napoleon.name, 3)

                orderId shouldBe 1
                getOrder(orderId)!!.cake shouldBe napoleon.copy(count = 3)
                verify(exactly = 1) { orderDao.addOrder(any()) }
                verify(exactly = 1) { orderDao.getOrder(any()) }
                verify(exactly = 2) { storageDao.getCake(any()) }
            }
            scenario("Проверка добавления некорректного заказа") {
                shouldThrow<Exception> { addOrder(napoleon.name, 3, HttpStatus.BAD_REQUEST) }
            }
            scenario("Проверка выполнения корректного заказа") {
                updateCake(napoleon.name, napoleon.cost, napoleon.count)
                val orderId = addOrder(napoleon.name, 3)

                completedOrder(orderId).completed shouldBe true
            }
            scenario("Проверка выполнения некорректного заказа") {
                shouldThrow<Exception> { completedOrder(orderId, HttpStatus.BAD_REQUEST) }
            }
        }
    }
}