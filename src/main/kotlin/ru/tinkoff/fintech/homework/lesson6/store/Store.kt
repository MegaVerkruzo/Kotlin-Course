package ru.tinkoff.fintech.homework.lesson6.store

import org.springframework.stereotype.Service
import ru.tinkoff.fintech.homework.lesson6.model.Cake
import ru.tinkoff.fintech.homework.lesson6.model.Order

@Service
class Store(private val storeClient: StoreClient) {
    fun getCakesList(): List<Cake> = storeClient.getCakesList()

    fun buyCakes(name: String, count: Int): Order =
        storeClient.buyCakes(name, count)
}
