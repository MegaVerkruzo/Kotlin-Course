package ru.tinkoff.fintech.homework.lesson6.order

import org.springframework.stereotype.Service
import ru.tinkoff.fintech.homework.lesson6.common.model.Cake
import ru.tinkoff.fintech.homework.lesson6.common.model.Order
import java.lang.IllegalArgumentException

@Service
class OrderDao {
    private val orders: MutableList<Order> = mutableListOf()

    fun getNumberOrder(): Int = orders.size

    fun getOrder(orderId: Int): Order {
        try {
            return orders[orderId]
        } catch (e: ArrayIndexOutOfBoundsException) {
            throw IllegalArgumentException("Попытка обратиться к заказу под несуществующим номером $orderId")
        }
    }

    fun completeOrder(orderId: Int) {
        orders[orderId] = Order(orders[orderId].orderId, orders[orderId].cake, true)
    }

    fun addOrder(cake: Cake, count: Int): Int {
        orders.add(Order(getNumberOrder(), Cake(cake.name, cake.cost, count), false))
        return orders.size - 1
    }
}
