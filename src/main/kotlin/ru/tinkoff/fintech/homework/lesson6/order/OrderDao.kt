package ru.tinkoff.fintech.homework.lesson6.order

import org.springframework.stereotype.Service
import ru.tinkoff.fintech.homework.lesson6.model.Cake
import ru.tinkoff.fintech.homework.lesson6.model.Order
import java.lang.IllegalArgumentException

@Service
class OrderDao {
    val orders: MutableList<Order> = mutableListOf(Order(0, Cake("cesar", 432.0, 3), false))

    fun getNumberOrder(): Int = orders.size

    fun getOrder(orderId: Int): Order {
        try {
            return orders[orderId]
        } catch (e: ArrayIndexOutOfBoundsException) {
            throw IllegalArgumentException("Попытка обратиться к заказу под несуществующим номером $orderId")
        }
    }

    fun completeOrder(orderId: Int) {
        val initialValue = orders[orderId]
        orders[orderId] = Order(initialValue.orderId, initialValue.cake, true)
    }

    fun addOrder(cake: Cake, count: Int): Int {
        orders.add(Order(getNumberOrder(), Cake(cake.name, cake.cost, count), false))
        return orders.size - 1
    }
}
