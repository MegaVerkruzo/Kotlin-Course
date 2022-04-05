package ru.tinkoff.fintech.homework.lesson6.company.service.client

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject
import ru.tinkoff.fintech.homework.lesson6.company.model.Cake

@Service
class StoreClient(
    val restTemplate: RestTemplate,
    @Value("\${store.list.address}") private val storeListClient: String
) {

    fun getCakesList(): Map<Cake, Int> = restTemplate.getForObject("$storeListClient$GET_CAKE_LIST", HttpMethod.GET)

    
}

private const val GET_CAKE_LIST = "/storage/list"