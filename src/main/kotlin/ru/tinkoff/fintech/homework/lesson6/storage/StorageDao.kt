package ru.tinkoff.fintech.homework.lesson6.storage

import org.springframework.stereotype.Service
import ru.tinkoff.fintech.homework.lesson6.common.model.Cake

@Service
class StorageDao {
    private val data: MutableMap<String, Cake> = mutableMapOf()

    fun getCakes(): Set<Cake> = data.values.toSet()

    fun getCake(name: String): Cake? = data[name]

    fun containCake(name: String, count: Int): Boolean = data.containsKey(name) && getCake(name)!!.count >= count

    fun updateCakeParams(cake: Cake) {
        data[cake.name] = cake
    }
}
