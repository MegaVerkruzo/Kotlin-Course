package ru.tinkoff.fintech.homework.lesson6.common.model

data class Order(
    val id: Int = 0,
    val cake: Cake,
    val completed: Boolean = false
)
