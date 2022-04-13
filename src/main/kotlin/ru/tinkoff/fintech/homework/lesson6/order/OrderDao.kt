package ru.tinkoff.fintech.homework.lesson6.order

import org.springframework.stereotype.Service
import ru.tinkoff.fintech.homework.lesson6.common.model.Cake
import ru.tinkoff.fintech.homework.lesson6.common.model.Order

@Service
class OrderDao {

    private var orderId: Int = 0

    private val orders: MutableMap<Int, Order> = mutableMapOf()

    fun addOrder(order: Order): Int {
        orders[++orderId] = order.copy(id = orderId)
        return orderId
    }

    fun getOrder(orderId: Int): Order? = orders[orderId]

    fun completedOrder(orderId: Int): Order {
        val order = orders[orderId]
        requireNotNull(order) { "Нет такого заказа в базе!" }
        val completedOrder = order.copy(completed = true)
        orders[orderId] = completedOrder
        return completedOrder
    }
}
