package ru.tinkoff.fintech.homework.lesson6.order

import io.kotest.core.spec.style.FeatureSpec
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import ru.tinkoff.fintech.homework.lesson6.model.Cake
import ru.tinkoff.fintech.homework.lesson6.model.Order
import ru.tinkoff.fintech.homework.lesson6.storage.StorageService
import ru.tinkoff.fintech.homework.lesson6.storage.StorageDao

@SpringBootTest
@AutoConfigureMockMvc
class OrderTest(private val mockMvc: MockMvc) : FeatureSpec() {

    @MockBean
    private val storageDao = mockk<StorageDao>()

    @MockBean
    private val orderDao = mockk<OrderDao>()

    private val storageService = StorageService(storageDao)

    override fun beforeEach(testCase: TestCase) {

    }

    override fun afterEach(testCase: TestCase, result: TestResult) {
        clearAllMocks()
    }

    init {

    }
}

