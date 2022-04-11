package ru.tinkoff.fintech.homework.lesson6.store

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.postForObject
import ru.tinkoff.fintech.homework.lesson6.common.model.Order

@Service
class OrderClient(
    private val restTemplate: RestTemplate,
    @Value("\${order.address}") private val orderAddress: String
) {
    fun addCakeOrder(name: String, count: Int): Order =
        restTemplate.postForObject("$orderAddress$ADD_ORDER", HttpMethod.POST, name, count)
}

private const val ADD_ORDER = "/order/add?name={name}&count={count}"