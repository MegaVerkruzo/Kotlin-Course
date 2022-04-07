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
        data[name]!!.count += count
    }

    fun changeCakePrice(name: String, cost: Double) {
        data[name]!!.cost = cost
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
        orders[orderId].packed = true
    }

    fun addOrder(cake: Cake, count: Int): Int {
        cake.count = count
        orders.add(Order(getNumberOrder(), cake, false))
        return orders.size - 1
    }

    private val data: MutableMap<String, Cake> = mutableMapOf("cesar" to Cake("cesar", 432.0, 20))
    private val orders: MutableList<Order> = mutableListOf()
}
