package ru.tinkoff.fintech.homework.lesson6.storage

import org.springframework.stereotype.Service
import ru.tinkoff.fintech.homework.lesson6.model.Cake
import ru.tinkoff.fintech.homework.lesson6.model.Order
import java.lang.IllegalArgumentException

@Service
class Storage(private val storageClient: StorageClient) {

    fun getCakesList(): List<Cake> = storageClient.getCakesList()

    fun consistCakeType(name: String): Boolean = storageClient.consistCakes(name, 0)

    fun consistCakes(name: String, count: Int) = storageClient.consistCakes(name, count)

    fun getCakesCount(name: String) = storageClient.getCakeCount(name)

    fun addCakes(name: String, cost: Double, count: Int) {
        if (consistCakeType(name)) {
            storageClient.changeCakePrice(name, cost)
            storageClient.addCakesCount(name, count)
        } else {
            storageClient.addNewCakeType(name, cost, count)
        }
    }

    fun changeCakesCount(name: String, count: Int) {
        if (!consistCakeType(name)) throw NoSuchElementException("Нельзя изменить кол-во тортов, неизвестного типа \"$name\"")

        storageClient.addCakesCount(name, count)
    }

    fun addOrder(name: String, count: Int): Order {
        try {
            val orderId = storageClient.addOrder(getCake(name), count)
            return getOrder(orderId)
        } catch (e: NoSuchElementException) {
            throw NoSuchElementException("Не удалось добавить заказ с несуществующим тортом \"$name\"")
        }
    }

    fun getCake(name: String): Cake {
        if (!consistCakeType(name)) throw NoSuchElementException("Не существует торта с таким названием \"$name\"")

        return Cake(name, storageClient.getCakeCost(name), storageClient.getCakeCount(name))
    }

    fun getOrder(orderId: Int): Order = storageClient.getOrder(orderId)

    fun completeOrder(orderId: Int) {
        val order = getOrder(orderId)
        if (order.cake.count > storageClient.getCakeCount(order.cake.name)) {
            throw IllegalArgumentException("Заказ нельзя выполнить из-за большого кол-ва тортов")
        }
        storageClient.completeOrder(orderId)
        changeCakesCount(order.cake.name, - order.cake.count)
    }
}
