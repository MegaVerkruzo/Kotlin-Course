package ru.tinkoff.fintech.homework.lesson6.company.service.client

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.exchange
import ru.tinkoff.fintech.homework.lesson6.company.model.Cake

@Service
class StorageClient(
    val restTemplate: RestTemplate,
    @Value("\${storage.list.address}") private val storageListClient: String
) {
    fun getCakesWithAmount(): Map<Cake, Int> =
        restTemplate.exchange<Map<Cake, Int>>("$storageListClient$GET_CAKE_MAP", HttpMethod.GET).body!!

}

private const val GET_CAKE_MAP = "/cake/amount"