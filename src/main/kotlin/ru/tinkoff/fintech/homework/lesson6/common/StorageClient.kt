package ru.tinkoff.fintech.homework.lesson6.common

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.*
import ru.tinkoff.fintech.homework.lesson6.model.Cake
import ru.tinkoff.fintech.homework.lesson6.model.Order

@Service
class StorageClient(
    private val restTemplate: RestTemplate,
    @Value("\${storage.address}") private val storageAddress: String
) {

    fun getCakesList(): Set<Cake> =
        restTemplate.exchange<Set<Cake>>("$storageAddress$GET_CAKE_LIST", HttpMethod.GET).body!!

    fun buyCakes(name: String, count: Int): Order =
        restTemplate.postForObject("$storageAddress$ADD_ORDER", HttpMethod.POST, name, count)

}

private const val GET_CAKE_LIST = "/storage/cake/list"
private const val ADD_ORDER = "/order/add?name={name}&count={count}"
