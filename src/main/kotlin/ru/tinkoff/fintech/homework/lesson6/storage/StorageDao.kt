package ru.tinkoff.fintech.homework.lesson6.storage

import org.springframework.stereotype.Service
import ru.tinkoff.fintech.homework.lesson6.common.model.Cake

@Service
class StorageDao {
    private val data: MutableMap<String, Cake> = mutableMapOf()

    fun getCakes(): Set<Cake> = data.values.toSet()

    fun getCake(name: String): Cake? = data[name]

    fun containCake(name: String, count: Int): Boolean = data.containsKey(name) && getCake(name)!!.count >= count

    fun addNewCakeType(cake: Cake) {
        data[cake.name] = cake
    }

    fun updateCakeCount(name: String, count: Int) {
        val cake: Cake? = getCake(name)
        requireNotNull(cake) { throw IllegalArgumentException("Не существующий тип торта") }
        data[name] = cake.copy(count = cake.count + count)
    }

    fun updateCakePrice(name: String, cost: Double) {
        val cake: Cake? = getCake(name)
        requireNotNull(cake) { throw IllegalArgumentException("Не существующий тип торта") }
        data[name] = cake.copy(cost = cost)
    }
}
