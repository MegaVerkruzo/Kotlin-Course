package ru.tinkoff.fintech.homework.lesson6.storage

import org.springframework.stereotype.Service
import ru.tinkoff.fintech.homework.lesson6.common.model.Cake
import java.lang.IllegalArgumentException

@Service
class StorageService(private val storageDao: StorageDao) {

    fun getCakes(): Set<Cake> = storageDao.getCakes()

    fun getCake(name: String): Cake? = storageDao.getCake(name)

    fun updateCake(name: String, cost: Double? = null, count: Int? = null): Cake {
        val oldCake = storageDao.getCake(name)
        return if (oldCake == null) {
            require(cost != null && count != null) { "Не хватает данных для торта" }
            addCake(name, cost, count)
        } else {
            updateCake(oldCake, cost, count)
        }
    }

    private fun addCake(name: String, cost: Double, count: Int): Cake {
        val newCake = Cake(name, cost, count)
        return storageDao.updateCake(newCake)
    }

    private fun updateCake(cake: Cake, cost: Double? = null, count: Int? = null): Cake {
        val actualCost = cost ?: cake.cost
        val actualCount = (count ?: 0) + cake.count
        require(actualCost > 0) { "Стоимость торта должна быть положительной" }
        require(actualCount >= 0) { "Кол-во тортов стало меньше 0" }
        val newCake = cake.copy(cost = actualCost, count = actualCount)
        return storageDao.updateCake(newCake)
    }
}
