package ru.tinkoff.fintech.homework.lesson6.company.storage

import org.springframework.stereotype.Service
import ru.tinkoff.fintech.homework.lesson6.company.model.Cake
import ru.tinkoff.fintech.homework.lesson6.company.model.Order
import java.lang.IllegalArgumentException

@Service
class Storage(private val storageClient: StorageClient) {

    fun getCakesList(): Map<String, Pair<Cake, Int>> = storageClient.getCakesList()

    fun consistCakeType(name: String): Boolean = storageClient.consistCakes(name, 0)

    fun consistCakes(name: String, count: Int) = storageClient.consistCakes(name, count)

    fun getCake(name: String): Cake {
        if (!consistCakeType(name)) throw NoSuchElementException("Не существует торта с таким названием \"$name\"")
        return Cake(name, storageClient.getCakeCost(name))
    }

    fun addCakes(name: String, cost: Double, count: Int) {
        if (consistCakeType(name)) {
            storageClient.changeCakePrice(name, cost)
            storageClient.addCakesCount(name, count)
        } else {
            storageClient.addNewCakeType(name, cost, count)
        }
    }

    fun deleteCakes(name: String, count: Int) {
        if (!consistCakeType(name))
            throw NoSuchElementException("Нельзя уменьшить кол-во несуществующих тортов с названием \"$name\"")

        storageClient.addCakesCount(name, -count)
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

    fun getOrder(orderId: Int): Order = storageClient.getOrder(orderId)

    fun doneOrder(orderId: Int) {
        val order = getOrder(orderId)
        storageClient.doneOrder(orderId)
        deleteCakes(order.cake.name, order.cakesCount)
    }
}
