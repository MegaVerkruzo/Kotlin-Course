package ru.tinkoff.fintech.homework.lesson6.storage

import org.springframework.stereotype.Service
import ru.tinkoff.fintech.homework.lesson6.common.model.Cake
import java.lang.IllegalArgumentException

@Service
class StorageService(private val storageDao: StorageDao) {

    fun getCakes(): Set<Cake> = storageDao.getCakes()

    fun getCake(name: String): Cake? {
        return storageDao.getCake(name)
    }

    fun updateCake(name: String, cost: Double?, count: Int?): Cake {
        var cake: Cake? = storageDao.getCake(name)
        cake = if (cake == null) {
            require(cost != null && count != null) { "Не хватает данных для торта" }
            Cake(name, cost, count)
        } else {
            val actualCost = cost ?: cake.cost
            val actualCount = (count ?: 0) + cake.count
            require(actualCount >= 0) { "Кол-во тортов стало меньше 0" }
            cake.copy(cost = actualCost, count = actualCount)
        }
        storageDao.updateCake(cake)
        return cake
    }
}
