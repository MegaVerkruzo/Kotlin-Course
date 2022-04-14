package ru.tinkoff.fintech.homework.lesson6.order

import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.mockk.verify
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.*
import org.springframework.http.HttpStatus
import ru.tinkoff.fintech.homework.lesson6.ParentTest
import ru.tinkoff.fintech.homework.lesson6.common.model.Order
import ru.tinkoff.fintech.homework.lesson6.napoleon
import java.nio.charset.StandardCharsets

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
class OrderTest(private val mockMvc: MockMvc, private val objectMapper: ObjectMapper) :
    ParentTest(mockMvc, objectMapper) {

    init {
        feature("Тестируем OrderController") {
            scenario("Проверка добавления 1-ого корректного заказа") {
                updateCake(napoleon.name, napoleon.cost, napoleon.count)

                val getOrder = addOrder(napoleon.name, 3)
                val orderId = getOrder.id
                getOrder(orderId)!!.cake shouldBe napoleon.copy(count = 3)

                orderId shouldBe 1
                verify(exactly = 1) { orderDao.addOrder(order) }
                verify(exactly = 1) { orderDao.getOrder(orderId) }
                verify(exactly = 2) { storageDao.getCake(napoleon.name) }
            }
            scenario("Проверка добавления некорректного заказа") {
                shouldThrow<JsonMappingException> { addOrder(napoleon.name, 3, HttpStatus.BAD_REQUEST) }
            }
            scenario("Проверка выполнения корректного заказа") {
                updateCake(napoleon.name, napoleon.cost, napoleon.count)
                val orderId = addOrder(napoleon.name, 3).id

                completeOrder(orderId).completed shouldBe true
            }
            scenario("Проверка выполнения некорректного заказа") {
                shouldThrow<JsonMappingException> { completeOrder(orderId, HttpStatus.BAD_REQUEST) }
            }
        }
    }

    protected fun addOrder(name: String, count: Int, expectedStatus: HttpStatus = HttpStatus.OK): Order =
        mockMvc.post("/order/add?name={name}&count={count}", name, count).readResponse(expectedStatus)

    protected fun completeOrder(orderId: Int, expectedStatus: HttpStatus = HttpStatus.OK): Order =
        mockMvc.post("/order/{orderId}/complete", orderId).readResponse(expectedStatus)

    private inline fun <reified T> ResultActionsDsl.readResponse(expectedStatus: HttpStatus = HttpStatus.OK): T = this
        .andExpect { status { isEqualTo(expectedStatus.value()) } }
        .andReturn().response.getContentAsString(StandardCharsets.UTF_8)
        .let { if (T::class == String::class) it as T else objectMapper.readValue(it) }
}

val order = Order(cake = napoleon.copy(count = 3))
