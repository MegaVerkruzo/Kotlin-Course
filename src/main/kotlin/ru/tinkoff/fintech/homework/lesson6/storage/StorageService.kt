package ru.tinkoff.fintech.homework.lesson6.storage

import org.springframework.stereotype.Service
import ru.tinkoff.fintech.homework.lesson6.common.model.Cake
import java.lang.IllegalArgumentException

@Service
class StorageService(private val storageDao: StorageDao) {

    fun getCakes(): Set<Cake> = storageDao.getCakes()

    fun getCake(name: String): Cake {
        val cake: Cake? = storageDao.getCake(name)
        requireNotNull(cake) { throw IllegalArgumentException("Не существует торта с таким названием \"$name\"") }
        return cake.copy(name = name)
    }

    fun containCake(name: String, count: Int): Boolean = storageDao.containCake(name, count)

    fun updateCakeParams(name: String, cost: Double?, count: Int?): Cake {
        var cake: Cake? = storageDao.getCake(name)
        if (cake == null) {
            require(cost != null && count != null) { throw IllegalArgumentException("Не хватает данных для торта") }
            cake = Cake(name, cost, count)
        } else {
            if (cost != null) {
                cake = cake.copy(cost = cost)
            }
            if (count != null) {
                cake = cake.copy(count = cake.count + count)
            }
        }
        storageDao.updateCakeParams(cake)
        return cake
    }
}
