package ru.tinkoff.fintech.homework.lesson6.storage

import org.springframework.stereotype.Service
import ru.tinkoff.fintech.homework.lesson6.model.Cake
import ru.tinkoff.fintech.homework.lesson6.model.Order
import java.lang.IllegalArgumentException

@Service
class StorageService(private val storageDao: StorageDao) {

    fun getCakesSet(): Set<Cake> = storageDao.getCakesSet()

    fun getCake(name: String): Cake {
        if (!consistCakes(name, 0)) throw IllegalArgumentException("Не существует торта с таким названием \"$name\"")

        return Cake(name, storageDao.getCake(name).cost, storageDao.getCake(name).count)
    }

    fun consistCakes(name: String, count: Int): Boolean = storageDao.consistCakes(name, count)

    fun addCakes(cake: Cake) {
        if (consistCakes(cake.name, 0)) {
            storageDao.updateCakesPrice(cake.name, cake.cost)
            storageDao.updateCakesCount(cake.name, cake.count + getCake(cake.name).count)
        } else {
            storageDao.addNewCakeType(cake)
        }
    }

    fun updateCakeParams(name: String, cost: Double?, count: Int?) {
        if (!consistCakes(name, 0)) {
            require(cost != null && count != null) { throw IllegalArgumentException("Не хватает данных для торта") }
            storageDao.addNewCakeType(Cake(name, cost, count))
        } else {
            if (cost != null) {
                storageDao.updateCakesPrice(name, cost)
            }
            if (count != null) {
                storageDao.updateCakesCount(name, count + getCake(name).count)
            }
        }
    }
}
