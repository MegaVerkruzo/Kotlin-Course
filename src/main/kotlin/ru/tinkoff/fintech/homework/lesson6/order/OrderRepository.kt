package ru.tinkoff.fintech.homework.lesson6.order

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import ru.tinkoff.fintech.homework.lesson6.common.model.Cake
import ru.tinkoff.fintech.homework.lesson6.common.model.Order
import java.time.Instant
import java.time.format.DateTimeFormatter

@Repository
class OrderRepository(private val jdbcTemplate: JdbcTemplate) : OrderDao {
    override fun addOrder(order: Order): Order {
        jdbcTemplate.query(
            "INSERT INTO demo (cake_name, cake_cost, cake_cost, cake_count, order_completed, created_on, finished) VALUES (${order.cake.name}, " +
                    "${order.cake.cost}, " +
                    "${order.cake.count}, " +
                    "${order.completed}, " +
                    "${DateTimeFormatter.ISO_INSTANT.format(Instant.now())})"
        ) { rs, _ ->

        }
        val newOrder = order.copy(id = jdbcTemplate.query(
            "SELECT order_id, cake_name FROM orders WHERE cake_name == ${order.cake.name}"
        ) {rs, _ ->
            rs.getInt("order_id")
        } [0])
        return newOrder
    }

    override fun getOrder(orderId: Int): Order? = jdbcTemplate.query(
        "SELECT * FROM orders WHERE order_id == $orderId"
    ) { rs, _ ->
        Order(
            rs.getInt("order_id"),
            Cake(
                rs.getString("cake_name"),
                rs.getDouble("cake_cost"),
                rs.getInt("cake_count")
            ),
            rs.getBoolean("order_completed")
        )
    }[0]

    override fun completedOrder(orderId: Int): Order = jdbcTemplate.query(
        "UPDATE orders SET order_completed = 1 WHERE id == $orderId"
    ) { rs, _ ->
        Order(
            rs.getInt("order_id"),
            Cake(
                rs.getString("cake_name"),
                rs.getDouble("cake_cost"),
                rs.getInt("cake_count")
            ),
            rs.getBoolean("order_completed")
        )
    }[0]
}