package ru.tinkoff.fintech.homework.lesson6.storage

import org.springframework.stereotype.Service
import ru.tinkoff.fintech.homework.lesson6.common.model.Cake

@Service
class StorageService(private val devStorageDao: StorageRepository) {

    fun getCakes(): Set<Cake> = devStorageDao.getCakes()

    fun getCake(name: String): Cake? = devStorageDao.getCake(name)

    fun updateCake(name: String, cost: Double? = null, count: Int? = null): Cake {
        val oldCake = devStorageDao.getCake(name)
        return if (oldCake == null) {
            require(cost != null && count != null) { "Не хватает данных для торта" }
            addCake(name, cost, count)
        } else {
            updateCake(oldCake, cost, count)
        }
    }

    private fun addCake(name: String, cost: Double, count: Int): Cake {
        require(cost > 0) { "Стоимость должна быть положительной" }
        require(count >= 0) { "Кол-во не должно быть отрицательным" }
        val newCake = Cake(name, cost, count)
        return devStorageDao.addCake(newCake)
    }

    private fun updateCake(cake: Cake, cost: Double? = null, count: Int? = null): Cake {
        val actualCost = cost ?: cake.cost
        val actualCount = (count ?: 0) + cake.count
        require(actualCost > 0) { "Стоимость торта должна быть положительной" }
        require(actualCount >= 0) { "Кол-во тортов стало меньше 0" }
        val newCake = cake.copy(cost = actualCost, count = actualCount)
        return devStorageDao.updateCake(newCake)
    }
}
