package ru.tinkoff.fintech.homework.lesson6.storage

import org.springframework.stereotype.Service
import ru.tinkoff.fintech.homework.lesson6.common.model.Cake

@Service
class StorageDao {

    private val data: MutableMap<String, Cake> = mutableMapOf("napoleon" to Cake("napoleon", 623.5, 8))

    fun getCakes(): Set<Cake> = data.values.toSet()

    fun getCake(name: String): Cake? = data[name]

    fun updateCake(cake: Cake): Cake {
        data[cake.name] = cake
        return cake
    }
}
