package ru.tinkoff.fintech.homework.lesson6.company.store

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.*
import ru.tinkoff.fintech.homework.lesson6.company.model.Cake
import ru.tinkoff.fintech.homework.lesson6.company.model.Order

@Service
class StoreClient(
    private val restTemplate: RestTemplate,
    @Value("\${store.list.address}") private val storeListClient: String
) {

    fun getCakesList(): Map<Cake, Int> = restTemplate.getForObject("$storeListClient$GET_CAKE_LIST", HttpMethod.GET)

    fun buyCakes(name: String, count: Int): Order {

        return restTemplate.postForObject("$storeListClient$ADD_ORDER", name, count)
    }


}

private const val GET_CAKE_LIST = "/storage/list"
private const val ADD_ORDER = "/storage/add-order?name={name}&count={count}"
