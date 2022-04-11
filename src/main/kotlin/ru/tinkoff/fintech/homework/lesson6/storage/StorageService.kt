package ru.tinkoff.fintech.homework.lesson6.storage

import org.springframework.stereotype.Service
import ru.tinkoff.fintech.homework.lesson6.common.model.Cake
import java.lang.IllegalArgumentException

@Service
class StorageService(private val storageDao: StorageDao) {

    fun getCakeList(): Set<Cake> = storageDao.getCakeList()

    fun getCake(name: String): Cake {
        if (!containCake(name, 0)) throw IllegalArgumentException("Не существует торта с таким названием \"$name\"")
        val currentCake: Cake = storageDao.getCake(name)

        return Cake(name, currentCake.cost, currentCake.count)
    }

    fun containCake(name: String, count: Int): Boolean = storageDao.containCake(name, count)

    fun addCake(cake: Cake) {
        if (containCake(cake.name, 0)) {
            storageDao.updateCakePrice(cake.name, cake.cost)
            storageDao.updateCakeCount(cake.name, cake.count)
        } else {
            storageDao.addNewCakeType(cake)
        }
    }

    fun updateCakeParams(name: String, cost: Double?, count: Int?): Cake {
        if (!containCake(name, 0)) {
            require(cost != null && count != null) { throw IllegalArgumentException("Не хватает данных для торта") }
            storageDao.addNewCakeType(Cake(name, cost, count))
        } else {
            if (cost != null) {
                storageDao.updateCakePrice(name, cost)
            }
            if (count != null) {
                storageDao.updateCakeCount(name, count)
            }
        }
        return getCake(name)
    }
}
