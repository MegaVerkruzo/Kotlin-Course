package ru.tinkoff.fintech.homework.lesson6.order

import org.springframework.stereotype.Service
import ru.tinkoff.fintech.homework.lesson6.common.model.Cake
import ru.tinkoff.fintech.homework.lesson6.common.model.Order

@Service
class OrderDao : OrderDaoInterface {

    private var orderId: Int = 0

    private val orders: MutableMap<Int, Order> = mutableMapOf()

    override fun addOrder(order: Order): Order {
        val newOrder = order.copy(id = ++orderId)
        orders[orderId] = newOrder
        return newOrder
    }

    override fun getOrder(orderId: Int): Order? = orders[orderId]

    override fun completedOrder(orderId: Int): Order {
        val order = orders[orderId]
        requireNotNull(order) { "Нет такого заказа в базе!" }
        val completedOrder = order.copy(completed = true)
        orders[orderId] = completedOrder
        return completedOrder
    }
}
