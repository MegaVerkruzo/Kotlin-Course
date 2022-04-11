package ru.tinkoff.fintech.homework.lesson6.storage

import org.springframework.stereotype.Service
import ru.tinkoff.fintech.homework.lesson6.common.model.Cake
import java.lang.IllegalArgumentException

@Service
class StorageService(private val storageDao: StorageDao) {

    fun getCakesList(): Set<Cake> = storageDao.getCakesList()

    fun getCake(name: String): Cake {
        if (!containCakes(name, 0)) throw IllegalArgumentException("Не существует торта с таким названием \"$name\"")
        val currentCake: Cake = storageDao.getCake(name)

        return Cake(name, currentCake.cost, currentCake.count)
    }

    fun containCakes(name: String, count: Int): Boolean = storageDao.containCakes(name, count)

    fun addCakes(cake: Cake) {
        if (containCakes(cake.name, 0)) {
            storageDao.updateCakesPrice(cake.name, cake.cost)
            storageDao.updateCakesCount(cake.name, cake.count)
        } else {
            storageDao.addNewCakeType(cake)
        }
    }

    fun updateCakesParams(name: String, cost: Double?, count: Int?): Cake {
        if (!containCakes(name, 0)) {
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
        return getCake(name)
    }
}
