package ru.tinkoff.fintech.homework.lesson6.storage

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import ru.tinkoff.fintech.homework.lesson6.model.Cake
import ru.tinkoff.fintech.homework.lesson6.model.Order

@Service
class StorageClient(
    private val restTemplate: RestTemplate,
    @Value("\${storage.list.address}") private val storageListClient: String
) {
    fun getCakesList(): List<Cake> = data.map { it.value }

    fun getCakeCount(name: String): Int = data[name]!!.count

    fun getCakeCost(name: String): Double = data[name]!!.cost

    fun consistCakes(name: String, count: Int): Boolean = data.containsKey(name) && getCakeCount(name) >= count

    fun addNewCakeType(name: String, cost: Double, count: Int) {
        data[name] = Cake(name, cost, count)
    }

    fun addCakesCount(name: String, count: Int) {
        val initialValue = data[name]!!
        data[name] = Cake(initialValue.name, initialValue.cost, initialValue.count + count)
    }

    fun changeCakePrice(name: String, cost: Double) {
        val initialValue = data[name]!!
        data[name] = Cake(initialValue.name, cost, initialValue.count)
    }

    fun getNumberOrder(): Int = orders.size

    fun getOrder(orderId: Int): Order {
        try {
            return orders[orderId]
        } catch (e: ArrayIndexOutOfBoundsException) {
            throw IndexOutOfBoundsException("Попытка обратиться к заказу под несуществующим номером $orderId")
        }
    }

    fun doneOrder(orderId: Int) {
        val initialValue = orders[orderId]
        orders[orderId] = Order(initialValue.orderId, initialValue.cake, true)
    }

    fun addOrder(cake: Cake, count: Int): Int {
        orders.add(Order(getNumberOrder(), Cake(cake.name, cake.cost, count), false))
        return orders.size - 1
    }

    private val data: MutableMap<String, Cake> = mutableMapOf("cesar" to Cake("cesar", 432.0, 20))
    private val orders: MutableList<Order> = mutableListOf()
}
