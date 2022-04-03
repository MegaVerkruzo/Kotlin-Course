package ru.tinkoff.fintech.homework.lesson6.company.service

import org.springframework.stereotype.Service
import ru.tinkoff.fintech.homework.lesson6.company.model.Cake
import ru.tinkoff.fintech.homework.lesson6.company.model.OrderedCake
import ru.tinkoff.fintech.homework.lesson6.company.service.client.CakeListClient

@Service
class Store(val cakeListClient: CakeListClient, val storage: Storage) {
    fun getCakesList(): List<Cake> = storage.getCakesList()

    fun getCake(name: String): Cake = storage.getCake(name)

    fun buyCake(name: String): OrderedCake? {
        if (storage.consistCake(name)) {
            return cakeListClient.buyCake(getCake(name))
        }
        return null
    }



}