package ru.tinkoff.fintech.homework.lesson6.company.storage

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import ru.tinkoff.fintech.homework.lesson6.company.model.Cake
import ru.tinkoff.fintech.homework.lesson6.company.model.Order
import java.lang.IllegalArgumentException

@Service
class StorageClient(
    private val restTemplate: RestTemplate,
    @Value("\${storage.list.address}") private val storageListClient: String
) {
    fun getCakesList(): MutableMap<String, Pair<Cake, Int>> = data.toMutableMap()

    fun getCakeCount(name: String): Int = data[name]!!.second

    fun getCakeCost(name: String): Double = data[name]!!.first.cost

    fun consistCakes(name: String, count: Int): Boolean = data.containsKey(name) && data[name]!!.second >= count

    fun addNewCakeType(name: String, cost: Double, count: Int) {
        data[name] = Pair(Cake(name, cost), count)
    }

    fun addCakesCount(name: String, count: Int) {
        data[name] = Pair(data[name]!!.first, data[name]!!.second + count)
    }

    fun changeCakePrice(name: String, cost: Double) {
        data[name] = Pair(Cake(name, cost), data[name]!!.second)
    }

    fun getNumberOrder(): Int = orders.size

    fun getOrder(orderId: Int): Order = orders[orderId]

    fun doneOrder(orderId: Int) {
        orders[orderId].packed = true
    }

    fun addOrder(cake: Cake, count: Int): Int {
        orders.add(Order(getNumberOrder(), cake, count, false))
        return orders.size - 1
    }

    private val data: MutableMap<String, Pair<Cake, Int>> = mutableMapOf("cesar" to Pair(Cake("cesar", 432.0), 20))
    private val orders: MutableList<Order> = mutableListOf()
}
