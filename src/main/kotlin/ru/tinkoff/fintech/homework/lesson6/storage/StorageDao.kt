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
        data[name] = Cake(data.getValue(name).name, data.getValue(name).cost, data.getValue(name).count + count)
    }

    fun updateCakePrice(name: String, cost: Double) {
        data[name] = Cake(data.getValue(name).name, cost, data.getValue(name).count)
    }
}
