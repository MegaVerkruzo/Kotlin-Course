package ru.tinkoff.fintech.homework.lesson6.company.service

import org.springframework.stereotype.Service
import ru.tinkoff.fintech.homework.lesson6.company.model.Cake
import ru.tinkoff.fintech.homework.lesson6.company.service.client.StorageClient

@Service
class Storage(val storageClient: StorageClient) {
    fun getCake(name: String): Cake = getCakesList().find { it.name == name }!!

    fun consistCake(name: String): Boolean = getCakesWithAmount()[getCake(name)]!! > 0

    fun getCakesWithAmount(): Map<Cake, Int> = storageClient.getCakesWithAmount()

    fun getCakesList(): List<Cake> = getCakesWithAmount().map { it.key }
}