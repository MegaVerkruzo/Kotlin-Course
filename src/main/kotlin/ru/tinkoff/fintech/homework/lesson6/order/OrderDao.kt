package ru.tinkoff.fintech.homework.lesson6.order

import org.springframework.stereotype.Service
import ru.tinkoff.fintech.homework.lesson6.common.model.Cake
import ru.tinkoff.fintech.homework.lesson6.common.model.Order
import java.lang.IllegalArgumentException

@Service
class OrderDao {

    private val orders: MutableList<Order> = mutableListOf()

    fun getNumberOrder(): Int = orders.size

    fun getOrder(orderId: Int): Order? = try {
        orders[orderId]
    } catch (e: ArrayIndexOutOfBoundsException) {
        null
    }

    fun completeOrder(orderId: Int) {
        val order: Order? = getOrder(orderId)
        requireNotNull(order) { "Нет такого заказа в базе!" }
        orders[orderId] = order.copy(completed = true)
    }

    fun addOrder(cake: Cake, count: Int): Int {
        val orderIndex: Int = getNumberOrder()
        val orderCake = cake.copy(count = count)
        orders.add(Order(orderIndex, orderCake, false))
        return orders.size - 1
    }
}
