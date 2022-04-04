//package ru.tinkoff.fintech.homework.lesson6.company.service.client
//
//import org.springframework.beans.factory.annotation.Value
//import org.springframework.http.HttpMethod
//import org.springframework.http.ResponseEntity
//import org.springframework.stereotype.Service
//import org.springframework.web.client.RestTemplate
//import org.springframework.web.client.exchange
//import ru.tinkoff.fintech.homework.lesson6.company.model.Cake
//import ru.tinkoff.fintech.homework.lesson6.company.model.OrderedCake
//
//@Service
//class CakeListClient(
//    val restTemplate: RestTemplate,
//    @Value("\${cake.list.address}") private val cakeListClient: String
//) {
//    fun buyCake(cake: Cake): OrderedCake? {
//        val orderId = restTemplate.exchange<Int>("$cakeListClient$BUY_CAKE", HttpMethod.POST).body!!
//        return OrderedCake(orderId, cake)
//    }
//}
//
//private const val GET_CAKE_LIST = "/cake/list"
//private const val BUY_CAKE = "/buy?name={name}"