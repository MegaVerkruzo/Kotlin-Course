package ru.tinkoff.fintech.homework.lesson6.company.store

import org.springframework.stereotype.Service
import ru.tinkoff.fintech.homework.lesson6.company.model.Cake
import ru.tinkoff.fintech.homework.lesson6.company.model.Order

@Service
class Store(val storeClient: StoreClient) {
    fun getCakesList(): Map<Cake, Int> = storeClient.getCakesList()

    fun buyCakes(name: String, count: Int): Order =
        storeClient.buyCakes(name, count)
}