package ru.tinkoff.fintech.homework.lesson6.company.service

import org.springframework.stereotype.Service
import ru.tinkoff.fintech.homework.lesson6.company.model.Cake
import ru.tinkoff.fintech.homework.lesson6.company.service.client.StorageClient
import java.lang.IllegalArgumentException

@Service
class Storage(val storageClient: StorageClient) {

    fun getCakesList(): Map<Cake, Int> = storageClient.getCakesList()

    fun consistCakeType(name: String): Boolean = storageClient.consistCakeType(name)

    fun consistCake(name: String): Boolean = storageClient.consistCake(name)

    fun addOrUpdateCake(name: String, cost: Double) {
        storageClient.addOrUpdateCake(name, cost)
    }

    fun changeCakesCount(name: String, count: Int) {
        require(consistCakeType(name)) { throw IllegalArgumentException("Такого типа торта нет на складе") }

        storageClient.changeCakesCount(name, count)
    }

    fun addCakes(name: String, cost: Double, count: Int) {
        addOrUpdateCake(name, cost)
        changeCakesCount(name, count)
    }

    fun deleteCakes(name: String, count: Int) {
        changeCakesCount(name, -count)
    }
}