package ru.tinkoff.fintech.homework.lesson6.store

import com.fasterxml.jackson.databind.ObjectMapper
import io.kotest.matchers.shouldBe
import io.mockk.verify
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.*
import ru.tinkoff.fintech.homework.lesson6.TestUtils
import ru.tinkoff.fintech.homework.lesson6.medovik
import ru.tinkoff.fintech.homework.lesson6.napoleon

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
class StoreTest(private val mockMvc: MockMvc, private val objectMapper: ObjectMapper) : TestUtils(mockMvc, objectMapper) {
    init {
        feature("Тестируем StoreController") {
            scenario("Проверка добавления заказа на несколько тортов") {
                updateCake(napoleon.name, napoleon.cost, napoleon.count) shouldBe napoleon
                updateCake(medovik.name, medovik.cost, medovik.count) shouldBe medovik
                addOrderStore(napoleon.name, 3) shouldBe 1
                getOrder(1)!!.cake shouldBe napoleon.copy(count = 3)
                verify(exactly = 3) { storageDao.getCake(any()) }
                verify(exactly = 1) { orderDao.addOrder(any()) }
                verify(exactly = 1) { orderDao.getOrder(any()) }
            }
        }
    }
}

