package ru.tinkoff.fintech.homework.lesson6.storage

import org.springframework.stereotype.Service
import ru.tinkoff.fintech.homework.lesson6.model.Cake
import ru.tinkoff.fintech.homework.lesson6.model.Order
import java.lang.IllegalArgumentException

@Service
class StorageService(private val storageDao: StorageDao) {

    fun getCakes(): MutableCollection<Cake> = storageDao.getCakes()

    fun consistCakeType(name: String): Boolean = storageDao.consistCakes(name, 0)

    fun consistCakes(name: String, count: Int): Boolean = storageDao.consistCakes(name, count)

    fun getCakesCount(name: String): Int = storageDao.getCake(name).count

    fun addCakes(cake: Cake) {
        if (consistCakeType(cake.name)) {
            storageDao.updateCakesPrice(cake.name, cake.cost)
            storageDao.updateCakesCount(cake.name, cake.count)
        } else {
            storageDao.addNewCakeType(cake)
        }
    }

    fun updateCakeParams(name: String, cost: Double?, count: Int?) {
        if (!consistCakeType(name)) {
            require(cost != null && count != null) { throw IllegalArgumentException("Не хватает данных для торта") }
            storageDao.addNewCakeType(Cake(name, cost, count))
        } else {
            if (cost != null) {
                storageDao.updateCakesPrice(name, cost)
            }
            if (count != null) {
                storageDao.updateCakesCount(name, count)
            }
        }
    }

    fun addOrder(name: String, count: Int): Order {
        try {
            val orderId = storageDao.addOrder(getCake(name), count)
            return getOrder(orderId)
        } catch (e: IllegalArgumentException) {
            throw IllegalArgumentException("Не удалось добавить заказ с несуществующим тортом \"$name\"")
        }
    }

    fun getCake(name: String): Cake {
        if (!consistCakeType(name)) throw IllegalArgumentException("Не существует торта с таким названием \"$name\"")

        return Cake(name, storageDao.getCake(name).cost, storageDao.getCake(name).count)
    }

    fun getOrder(orderId: Int): Order = storageDao.getOrder(orderId)

    fun completeOrder(orderId: Int) {
        val order = getOrder(orderId)
        if (order.cake.count > storageDao.getCake(order.cake.name).count) {
            throw IllegalArgumentException("Заказ нельзя выполнить из-за большого кол-ва тортов")
        }
        storageDao.completeOrder(orderId)
        updateCakeParams(order.cake.name, null, -order.cake.count)
    }
}
