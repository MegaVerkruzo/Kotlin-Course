package ru.tinkoff.fintech.homework.lesson6.order

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import ru.tinkoff.fintech.homework.lesson6.common.model.Cake
import ru.tinkoff.fintech.homework.lesson6.common.model.Order
import java.sql.ResultSet
import java.time.Instant
import java.time.format.DateTimeFormatter

@Repository
class OrderRepositoryImpl(private val jdbcTemplate: JdbcTemplate) : OrderDao {
    private var orderId: Int = 0

    override fun addOrder(order: Order): Order {
        val newOrder = order.copy(id = ++orderId)
        jdbcTemplate.query(
            "insert into orders values (${newOrder.id}, " +
                    "${newOrder.cake.name}, " +
                    "${newOrder.cake.cost}," +
                    "${newOrder.cake.count}," +
                    "${newOrder.completed}," +
                    "${DateTimeFormatter.ISO_INSTANT.format(Instant.now())})"
        ) {rs, _ ->

        }
        return newOrder
    }

    override fun getOrder(orderId: Int): Order? {
        return jdbcTemplate.query(
            "SELECT * FROM orders WHERE order_id == $orderId"
        ) {rs, _ ->
            Order(
                rs.getInt("order_id"),
                Cake(
                    rs.getString("cake_name"),
                    rs.getDouble("cake_cost"),
                    rs.getInt("cake_count")
                ),
                rs.getBoolean("order_completed")
            )
        } [0]
    }

    override fun completedOrder(orderId: Int): Order {
        return jdbcTemplate.query(
            "UPDATE orders SET Name = \"fdsf\" WHERE id == 1 "
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
        } [0]
    }
}