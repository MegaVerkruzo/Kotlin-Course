package ru.tinkoff.fintech.homework.lesson6.company.service.client

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForEntity
import org.springframework.web.client.getForObject
import org.springframework.web.client.postForEntity
import ru.tinkoff.fintech.homework.lesson6.company.model.Cake
import ru.tinkoff.fintech.homework.lesson6.company.model.Order

@Service
class StoreClient(
    val restTemplate: RestTemplate,
    @Value("\${store.list.address}") private val storeListClient: String
) {

    fun getCakesList(): Map<Cake, Int> = restTemplate.getForObject("$storeListClient$GET_CAKE_LIST", HttpMethod.GET)

    fun buyCakes(name: String, count: Int): Order? =
        restTemplate.getForObject("$storeListClient$ADD_ORDER", mapOf("name" to name, "count" to count))
}

private const val GET_CAKE_LIST = "/storage/list"
private const val ADD_ORDER = "/add-order"