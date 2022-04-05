package ru.tinkoff.fintech.homework.lesson6.company.model

data class Order(
    val orderId: Int,
    val cake: Cake,
    val cakesCount: Int,
    var packed: Boolean
)