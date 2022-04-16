package ru.tinkoff.fintech.homework.lesson6.order

import ru.tinkoff.fintech.homework.lesson6.common.model.Order

interface OrderDaoInterface {

    fun addOrder(order: Order): Order

    fun getOrder(orderId: Int): Order?

    fun completedOrder(orderId: Int): Order
}