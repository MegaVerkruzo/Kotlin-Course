package ru.tinkoff.fintech.homework.lesson6.company.service

import org.springframework.stereotype.Service
import ru.tinkoff.fintech.homework.lesson6.company.model.Cake
import ru.tinkoff.fintech.homework.lesson6.company.model.Order
import ru.tinkoff.fintech.homework.lesson6.company.service.client.StoreClient

@Service
class Store(val storeClient: StoreClient, val storage: Storage) {
    fun getCakesList(): Map<Cake, Int> = storeClient.getCakesList()

    fun buyCakes(name: String, count: Int): Order =
        storage.addOrder(name, count)
}