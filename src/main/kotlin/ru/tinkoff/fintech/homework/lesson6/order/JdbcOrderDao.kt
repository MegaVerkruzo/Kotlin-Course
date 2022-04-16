package ru.tinkoff.fintech.homework.lesson6.order

import org.springframework.context.annotation.Profile
import org.springframework.jdbc.core.JdbcTemplate
import ru.tinkoff.fintech.homework.lesson6.common.model.Order

@Profile("jdbc")
class JdbcOrderDao(private val jdbcTemplate: JdbcTemplate) : OrderDao {
    override fun addOrder(order: Order): Order {
        TODO("Not yet implemented")
    }

    override fun getOrder(orderId: Int): Order? {
        TODO("Not yet implemented")
    }

    override fun completedOrder(orderId: Int): Order {
        TODO("Not yet implemented")
    }
}