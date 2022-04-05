package ru.tinkoff.fintech.homework.lesson6.company.storage

import org.springframework.stereotype.Service
import ru.tinkoff.fintech.homework.lesson6.company.model.Cake
import ru.tinkoff.fintech.homework.lesson6.company.model.Order
import java.lang.IllegalArgumentException

@Service
class Storage(private val storageClient: StorageClient) {

    fun getCakesList(): Map<String, Pair<Cake, Int>> = storageClient.getCakesList()

    fun consistCakeType(name: String): Boolean = storageClient.consistCakes(name, 0)

    fun getCake(name: String): Cake = Cake(name, storageClient.getCakeCost(name))

    fun consistCakes(name: String, count: Int) = storageClient.consistCakes(name, count)

    fun addCakes(name: String, cost: Double, count: Int) {
        if (storageClient.consistCakes(name, 0)) {
            storageClient.changeCakePrice(name, cost)
            storageClient.addCakesCount(name, count)
        } else {
            storageClient.addNewCakeType(name, cost, count)
        }
    }

    fun deleteCakes(name: String, count: Int) {
        storageClient.addCakesCount(name, -count)
    }

    fun addOrder(name: String, count: Int): Order {
        val orderId = storageClient.addOrder(getCake(name), count)
        return getOrder(orderId)
    }

    fun getOrder(orderId: Int): Order {
        return storageClient.getOrder(orderId)
    }

    fun doneOrder(orderId: Int) {
        storageClient.doneOrder(orderId)
        val order = getOrder(orderId)
        deleteCakes(order.cake.name, order.cakesCount)
    }

    fun changeCakesCount(name: String, count: Int) {
        storageClient.addCakesCount(name, count)
    }
}
