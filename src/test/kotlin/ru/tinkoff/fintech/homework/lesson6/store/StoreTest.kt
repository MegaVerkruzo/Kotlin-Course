package ru.tinkoff.fintech.homework.lesson6.store

import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.shouldBe
import io.mockk.verify
import org.springframework.http.HttpStatus
import org.springframework.test.web.servlet.*
import ru.tinkoff.fintech.homework.lesson6.ParentTest
import ru.tinkoff.fintech.homework.lesson6.common.model.Cake
import ru.tinkoff.fintech.homework.lesson6.common.model.Order
import ru.tinkoff.fintech.homework.lesson6.medovik
import ru.tinkoff.fintech.homework.lesson6.napoleon
import ru.tinkoff.fintech.homework.lesson6.shokoladnie
import java.nio.charset.StandardCharsets

class StoreTest(private val mockMvc: MockMvc, private val objectMapper: ObjectMapper) :
    ParentTest(mockMvc, objectMapper) {

    init {
        feature("Тестируем StoreController") {
            scenario("Проверка добавления корректного заказа на несколько тортов") {
                updateCake(napoleon.name, napoleon.cost, napoleon.count)
                updateCake(medovik.name, medovik.cost, medovik.count)
                val orderId = addOrderStore(napoleon.name, 3).id

                getOrder(orderId)!!.cake shouldBe napoleon.copy(count = 3)
                verify(exactly = 2) { storageDao.getCake(napoleon.name) }
                verify(exactly = 1) { storageDao.getCake(medovik.name) }
                verify(exactly = 1) { orderDao.addOrder(order) }
                verify(exactly = 1) { orderDao.getOrder(orderId) }
            }
            scenario("Проверка добавления некорректного заказа на несуществующий торт") {
                shouldThrow<JsonMappingException> { addOrderStore(napoleon.name, 10, HttpStatus.BAD_REQUEST) }
            }
            scenario("Проверка вывода списка тортов") {
                updateCake(napoleon.name, napoleon.cost, napoleon.count)
                updateCake(medovik.name, medovik.cost, medovik.count)
                updateCake(shokoladnie.name, shokoladnie.cost, shokoladnie.count)

                getCakesStore() shouldContainAll setOf(napoleon, medovik, shokoladnie)
            }
        }
    }

    protected fun getCakesStore(): Set<Cake> =
        mockMvc.get("/store/cake/list").readResponse()

    protected fun addOrderStore(name: String, count: Int, expectedStatus: HttpStatus = HttpStatus.OK): Order =
        mockMvc.post("/store/cake/add-order?name={name}&count={count}", name, count).readResponse(expectedStatus)

    private inline fun <reified T> ResultActionsDsl.readResponse(expectedStatus: HttpStatus = HttpStatus.OK): T = this
        .andExpect { status { isEqualTo(expectedStatus.value()) } }
        .andReturn().response.getContentAsString(StandardCharsets.UTF_8)
        .let { if (T::class == String::class) it as T else objectMapper.readValue(it) }
}

private val order = Order(cake = napoleon.copy(count = 3))
