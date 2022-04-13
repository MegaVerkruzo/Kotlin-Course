package ru.tinkoff.fintech.homework.lesson6.storage

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
class StorageTest(private val mockMvc: MockMvc, private val objectMapper: ObjectMapper) : TestUtils(mockMvc, objectMapper) {

    init {
        feature("Тестируем StorageController") {
            scenario("Проверка добавления нескольких тортов") {
                updateCake(napoleon.name, napoleon.cost, napoleon.count) shouldBe napoleon
                updateCake(medovik.name, medovik.cost, medovik.count) shouldBe medovik
                getCake(napoleon.name) shouldBe napoleon
                getCake(medovik.name) shouldBe medovik
                verify(exactly = 4) { storageDao.getCake(any()) }
                verify(exactly = 2) { storageDao.updateCake(any()) }
            }
            scenario("Проверка обновления стоимости и кол-ва тортов") {
                updateCake(napoleon.name, napoleon.cost, napoleon.count) shouldBe napoleon
                updateCake(napoleon.name, null, 3).count shouldBe napoleon.count + 3
                updateCake(napoleon.name, 43.3, null).cost shouldBe 43.3
                verify(exactly = 3) { storageDao.getCake(any()) }
                verify(exactly = 3) { storageDao.updateCake(any()) }
            }
        }
    }
}

