package ru.tinkoff.fintech.homework.lesson6.storage

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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
class StorageTest(private val mockMvc: MockMvc, private val objectMapper: ObjectMapper) : TestUtils(mockMvc, objectMapper) {

    init {
        feature("Тестируем StorageController") {
            scenario("Проверка добавления нескольких тортов") {
                updateCake(napoleon.name, napoleon.cost, napoleon.count)
                updateCake(medovik.name, medovik.cost, medovik.count)

                getCake(napoleon.name) shouldBe napoleon
                getCake(medovik.name) shouldBe medovik
                verify(exactly = 4) { storageDao.getCake(any()) }
                verify(exactly = 2) { storageDao.updateCake(any()) }
            }
            scenario("Проверка нахождения несуществующего торта") {
                println(getCake("NoCake"))
            }
            scenario("Проверка корректного обновления стоимости и кол-ва тортов") {
                updateCake(napoleon.name, napoleon.cost, napoleon.count) shouldBe napoleon
                updateCake(napoleon.name, null, 3).count shouldBe napoleon.count + 3
                updateCake(napoleon.name, 43.3, null).cost shouldBe 43.3
                val cake = getCake(napoleon.name)!!

                cake.count shouldBe napoleon.count + 3
                cake.cost shouldBe 43.3
                verify(exactly = 4) { storageDao.getCake(any()) }
                verify(exactly = 3) { storageDao.updateCake(any()) }
            }
            scenario("Проверка некорректного обновления стоимости и кол-ва тортов") {
                updateCake(napoleon.name, napoleon.cost, napoleon.count)

                shouldThrow<Exception> { updateCake(napoleon.name, null, -100, HttpStatus.BAD_REQUEST) }
                shouldThrow<Exception> { updateCake(napoleon.name, -43.3, null, HttpStatus.BAD_REQUEST) }
            }
            scenario("Проверка выдачи списка тортов") {
                updateCake(napoleon.name, napoleon.cost, napoleon.count)
                updateCake(medovik.name, medovik.cost, medovik.count)

                getCakes() shouldContainAll setOf(napoleon, medovik)
            }
        }
    }
}

