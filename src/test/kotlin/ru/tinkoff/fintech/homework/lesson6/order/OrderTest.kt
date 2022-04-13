package ru.tinkoff.fintech.homework.lesson6.order

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.ninjasquad.springmockk.MockkBean
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FeatureSpec
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import org.springframework.test.web.servlet.*
import ru.tinkoff.fintech.homework.lesson6.common.StorageClient
import ru.tinkoff.fintech.homework.lesson6.common.model.Cake
import ru.tinkoff.fintech.homework.lesson6.common.model.Order
import ru.tinkoff.fintech.homework.lesson6.storage.StorageController
import ru.tinkoff.fintech.homework.lesson6.storage.StorageService
import ru.tinkoff.fintech.homework.lesson6.storage.StorageDao
import ru.tinkoff.fintech.homework.lesson6.store.OrderClient
import ru.tinkoff.fintech.homework.lesson6.store.StoreController
import java.nio.charset.StandardCharsets.UTF_8
import io.kotest.core.extensions.Extension
import io.kotest.extensions.spring.SpringExtension
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