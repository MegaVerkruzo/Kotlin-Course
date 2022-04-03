package ru.tinkoff.fintech.homework.lesson6.company.service.client

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import ru.tinkoff.fintech.homework.lesson6.company.model.Cake

@Service
class CakeListClient(
    val restTemplate: RestTemplate,
    @Value("cake.list.address") private val cakeListClient: String
) {

    fun getCakesList(): Set<Cake> =
        restTemplate.exchange<Set<Cake>>("")

    fun consistCake(): Boolean {

    }
}