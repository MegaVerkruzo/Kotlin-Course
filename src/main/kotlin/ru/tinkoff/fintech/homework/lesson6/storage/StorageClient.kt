package ru.tinkoff.fintech.homework.lesson6.storage

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import ru.tinkoff.fintech.homework.lesson6.model.CakeResponse
import ru.tinkoff.fintech.homework.lesson6.model.Order

@Service
class StorageClient(
    private val restTemplate: RestTemplate,
    @Value("\${storage.list.address}") private val storageListClient: String
) {
    fun getCakesList(): List<CakeResponse> = data.map { it.value }

    fun getCakeCount(name: String): Int = data[name]!!.count

    fun getCakeCost(name: String): Double = data[name]!!.cost

    fun consistCakes(name: String, count: Int): Boolean = data.containsKey(name) && getCakeCount(name) >= count

    fun addNewCakeType(name: String, cost: Double, count: Int) {
        data[name] = CakeResponse(name, cost, count)
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

    fun addOrder(cakeResponse: CakeResponse, count: Int): Int {
        cakeResponse.count = count
        orders.add(Order(getNumberOrder(), cakeResponse, false))
        return orders.size - 1
    }

    private val data: MutableMap<String, CakeResponse> = mutableMapOf("cesar" to CakeResponse("cesar", 432.0, 20))
    private val orders: MutableList<Order> = mutableListOf()
}
