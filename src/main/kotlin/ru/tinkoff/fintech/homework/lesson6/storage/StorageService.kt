package ru.tinkoff.fintech.homework.lesson6.storage

import org.springframework.stereotype.Service
import ru.tinkoff.fintech.homework.lesson6.model.Cake
import ru.tinkoff.fintech.homework.lesson6.model.Order
import ru.tinkoff.fintech.homework.lesson6.store.StorageClient
import java.lang.IllegalArgumentException

@Service
class StorageService(private val storageClient: StorageClient) {

    fun getCakesList(): List<Cake> = storageClient.getCakesList()

    fun consistCakeType(name: String): Boolean = storageClient.consistCakes(name, 0)

    fun consistCakes(name: String, count: Int): Boolean = storageClient.consistCakes(name, count)

    fun getCakesCount(name: String): Int = storageClient.getCakeCount(name)

    fun addCakes(cake: Cake) {
        if (consistCakeType(cake.name)) {
            storageClient.changeCakePrice(cake.name, cake.cost)
            storageClient.updateCakesCount(cake.name, cake.count)
        } else {
            storageClient.addNewCakeType(cake)
        }
    }

    fun updateCakesCount(name: String, count: Int) {
        if (!consistCakeType(name)) throw IllegalArgumentException("Нельзя изменить кол-во тортов, неизвестного типа \"$name\"")

        storageClient.updateCakesCount(name, count)
    }

    fun addOrder(name: String, count: Int): Order {
        try {
            val orderId = storageClient.addOrder(getCake(name), count)
            return getOrder(orderId)
        } catch (e: IllegalArgumentException) {
            throw IllegalArgumentException("Не удалось добавить заказ с несуществующим тортом \"$name\"")
        }
    }

    fun getCake(name: String): Cake {
        if (!consistCakeType(name)) throw IllegalArgumentException("Не существует торта с таким названием \"$name\"")

        return Cake(name, storageClient.getCakeCost(name), storageClient.getCakeCount(name))
    }

    fun getOrder(orderId: Int): Order = storageClient.getOrder(orderId)

    fun completeOrder(orderId: Int) {
        val order = getOrder(orderId)
        if (order.cake.count > storageClient.getCakeCount(order.cake.name)) {
            throw IllegalArgumentException("Заказ нельзя выполнить из-за большого кол-ва тортов")
        }
        storageClient.completeOrder(orderId)
        updateCakesCount(order.cake.name, - order.cake.count)
    }
}
