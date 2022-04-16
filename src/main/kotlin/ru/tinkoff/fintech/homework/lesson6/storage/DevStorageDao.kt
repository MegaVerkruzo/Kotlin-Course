package ru.tinkoff.fintech.homework.lesson6.storage

import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import ru.tinkoff.fintech.homework.lesson6.common.model.Cake

@Service
@Profile("dev", "cakesShop")
class DevStorageDao: StorageDao {

    private val data: MutableMap<String, Cake> = mutableMapOf()

    override fun getCakes(): Set<Cake> = data.values.toSet()

    override fun getCake(name: String): Cake? = data[name]

    override fun updateCake(cake: Cake): Cake {
        data[cake.name] = cake
        return cake
    }
}
