package ru.tinkoff.fintech.homework.lesson6.model

data class Order(
    val orderId: Int,
    val cakeResponse: CakeResponse,
    var packed: Boolean
)
