package ru.tinkoff.fintech.homework.lesson6.order

import org.springframework.stereotype.Service
import ru.tinkoff.fintech.homework.lesson6.common.model.Cake
import ru.tinkoff.fintech.homework.lesson6.common.model.Order

@Service
class OrderDao {

    private var orderId: Int = 0

    private val orders: MutableMap<Int, Order> = mutableMapOf()

    fun addOrder(order: Order): Int {
        orders[++orderId] = order
        return orderId
    }

    fun getOrder(orderId: Int): Order? = orders[orderId]

    fun completeOrder(orderId: Int): Order {
        val order = orders[orderId]
        requireNotNull(order) { "Нет такого заказа в базе!" }
        val finishedOrder = order.copy(completed = true)
        orders[orderId] = finishedOrder
        return finishedOrder
    }
}
