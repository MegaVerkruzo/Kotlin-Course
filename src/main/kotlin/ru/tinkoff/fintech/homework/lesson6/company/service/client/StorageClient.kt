package ru.tinkoff.fintech.homework.lesson6.company.service.client

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import ru.tinkoff.fintech.homework.lesson6.company.model.Cake
import java.lang.IllegalArgumentException

@Service
class StorageClient(
    val restTemplate: RestTemplate,
    @Value("\${storage.list.address}") private val storageListClient: String
) {
    fun getCakesList(): MutableMap<Cake, Int> {
        return data
    }

    fun consistCakeType(name: String): Boolean = cakeCost.containsKey(name)

    fun consistCake(name: String): Boolean {
        require(cakeCost.containsKey(name) && data.containsKey(cakeCost[name])) { return false }

        return data[cakeCost[name]]!! > 0
    }

    fun changeCakesCount(name: String, count: Int) {
        require(cakeCost.containsKey(name) && data.containsKey(cakeCost[name])) { throw IllegalArgumentException() }

        data[cakeCost[name]!!] = count + data[cakeCost[name]!!]!!
    }

    fun addOrUpdateCake(name: String, cost: Double) {
        var count = 0
        if (cakeCost.containsKey(name)) {
            require(data.containsKey(cakeCost[name])) { throw IllegalArgumentException() }

            count = data[cakeCost[name]]!!
            data.remove(cakeCost[name]!!)
        }
        cakeCost.remove(name)
        cakeCost[name] = Cake(name, cost)
        data[cakeCost[name]!!] = count
    }

    private val cakeCost: MutableMap<String, Cake> = mutableMapOf("cesar" to Cake("cesar", 43.0))
    private val data: MutableMap<Cake, Int> = mutableMapOf(Cake("cesar", 43.0) to 50)
}
