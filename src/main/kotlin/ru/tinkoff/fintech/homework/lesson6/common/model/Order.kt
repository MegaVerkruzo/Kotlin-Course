package ru.tinkoff.fintech.homework.lesson6.common.model

data class Order(
    val id: Int,
    val cake: Cake,
    val completed: Boolean = false
)
