package ru.tinkoff.fintech.homework.lesson6.store

import ru.tinkoff.fintech.homework.lesson6.model.Cake
import ru.tinkoff.fintech.homework.lesson6.model.Order

val data: MutableMap<String, Cake> = mutableMapOf("cesar" to Cake("cesar", 432.0, 20))
val orders: MutableList<Order> = mutableListOf()