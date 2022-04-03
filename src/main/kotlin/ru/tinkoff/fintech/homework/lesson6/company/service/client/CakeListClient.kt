package ru.tinkoff.fintech.homework.lesson6.company.service.client

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.exchange
import ru.tinkoff.fintech.homework.lesson6.company.model.Cake

@Service
class CakeListClient(
    val restTemplate: RestTemplate,
    @Value("\${cake.list.address}") private val cakeListClient: String
) {

    fun getCakesList(): Set<Cake> =
        restTemplate.exchange<Set<Cake>>("$cakeListClient$GET_CAKE_LIST", HttpMethod.GET).body!!

    fun getCake(id: Int): Cake =
        getCakesList().find { it.id == id }!!

    fun consistCake(cake: Cake): Boolean =
        getCakesList().contains(cake)
}

private const val GET_CAKE_LIST = "/cake/list"
