package ru.tinkoff.fintech.homework.lesson6.storage

import org.springframework.stereotype.Service
import ru.tinkoff.fintech.homework.lesson6.model.Cake
import ru.tinkoff.fintech.homework.lesson6.model.Order
import ru.tinkoff.fintech.homework.lesson6.store.data
import ru.tinkoff.fintech.homework.lesson6.store.orders
import java.lang.IllegalArgumentException

@Service
class StorageDao {
    fun getCakes(): MutableCollection<Cake> = data.values

    fun getCake(name: String): Cake = data[name]!!

    fun consistCakes(name: String, count: Int): Boolean = data.containsKey(name) && getCake(name).count >= count

    fun addNewCakeType(cake: Cake) {
        data[cake.name] = cake
    }

    fun updateCakesCount(name: String, count: Int) {
        val initialValue = data[name]!!
        data[name] = Cake(initialValue.name, initialValue.cost, initialValue.count + count)
    }

    fun updateCakesPrice(name: String, cost: Double) {
        val initialValue = data[name]!!
        data[name] = Cake(initialValue.name, cost, initialValue.count)
    }

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
