package ru.tinkoff.fintech.homework.lesson6.company.service

import org.springframework.stereotype.Service
import ru.tinkoff.fintech.homework.lesson6.company.model.Cake
import ru.tinkoff.fintech.homework.lesson6.company.model.OrderedCake
import ru.tinkoff.fintech.homework.lesson6.company.service.client.CakeListClient

@Service
class Store(val cakeListClient: CakeListClient) {
    fun getCakesList(): Map<Cake, Int> = cakeListClient.getCakesList()


}