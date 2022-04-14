package ru.tinkoff.fintech.homework.lesson6.storage

import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.shouldBe
import io.mockk.verify
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.test.web.servlet.*
import ru.tinkoff.fintech.homework.lesson6.ParentTest
import ru.tinkoff.fintech.homework.lesson6.common.model.Cake
import ru.tinkoff.fintech.homework.lesson6.medovik
import ru.tinkoff.fintech.homework.lesson6.napoleon
import ru.tinkoff.fintech.homework.lesson6.shokoladnie
import java.nio.charset.StandardCharsets

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
class StorageTest(private val mockMvc: MockMvc, private val objectMapper: ObjectMapper) :
    ParentTest(mockMvc, objectMapper) {

    init {
        feature("Тестируем StorageController") {
            scenario("Проверка добавления нескольких тортов") {
                updateCake(napoleon.name, napoleon.cost, napoleon.count)
                updateCake(medovik.name, medovik.cost, medovik.count)

                getCake(napoleon.name) shouldBe napoleon
                getCake(medovik.name) shouldBe medovik
                verify(exactly = 2) { storageDao.getCake(napoleon.name) }
                verify(exactly = 2) { storageDao.getCake(medovik.name) }
                verify(exactly = 2) { storageDao.updateCake(any()) }
            }
            scenario("Проверка нахождения несуществующего торта") {
                shouldThrow<JsonMappingException> { getCake("NoCake") }
            }
            scenario("Проверка корректного обновления стоимости и кол-ва тортов") {
                updateCake(napoleon.name, napoleon.cost, napoleon.count) shouldBe napoleon
                updateCake(napoleon.name, null, 3).count shouldBe napoleon.count + 3
                updateCake(napoleon.name, 43.3, null).cost shouldBe 43.3
                val cake = getCake(napoleon.name)!!

                cake.count shouldBe napoleon.count + 3
                cake.cost shouldBe 43.3
                verify(exactly = 4) { storageDao.getCake(napoleon.name) }
                verify(exactly = 3) { storageDao.updateCake(any()) }
            }
            scenario("Проверка некорректного обновления стоимости и кол-ва тортов") {
                updateCake(napoleon.name, napoleon.cost, napoleon.count)

                shouldThrow<JsonMappingException> { updateCake(napoleon.name, null, -100, HttpStatus.BAD_REQUEST) }
                shouldThrow<JsonMappingException> { updateCake(napoleon.name, -43.3, null, HttpStatus.BAD_REQUEST) }
            }
            scenario("Проверка выдачи списка тортов") {
                updateCake(napoleon.name, napoleon.cost, napoleon.count)
                updateCake(medovik.name, medovik.cost, medovik.count)
                updateCake(shokoladnie.name, shokoladnie.cost, shokoladnie.count)

                getCakes() shouldContainAll setOf(napoleon, medovik, shokoladnie)
            }
        }
    }

    protected fun getCakes(): Set<Cake> =
        mockMvc.get("/storage/cake/list").readResponse()

    private inline fun <reified T> ResultActionsDsl.readResponse(expectedStatus: HttpStatus = HttpStatus.OK): T = this
        .andExpect { status { isEqualTo(expectedStatus.value()) } }
        .andReturn().response.getContentAsString(StandardCharsets.UTF_8)
        .let { if (T::class == String::class) it as T else objectMapper.readValue(it) }
}

