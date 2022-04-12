package ru.tinkoff.fintech.homework.lesson6.order

import org.springframework.stereotype.Service
import ru.tinkoff.fintech.homework.lesson6.common.model.Cake
import ru.tinkoff.fintech.homework.lesson6.common.model.Order

@Service
class OrderDao {

    final var orderId: Int = 1
    private set
    
    private val orders: MutableMap<Int, Order> = mutableMapOf()

    fun getOrder(orderId: Int): Order? = orders[orderId]

    fun completeOrder(orderId: Int) {
        val order: Order? = getOrder(orderId)
        requireNotNull(order) { "Нет такого заказа в базе!" }
        orders[orderId] = order.copy(completed = true)
    }

    fun addOrder(order: Order): Int {
        orders[orderId] = order
        return orderId++
    }
}
