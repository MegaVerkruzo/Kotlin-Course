package ru.tinkoff.fintech.homework.lesson6.order

import org.springframework.stereotype.Service
import ru.tinkoff.fintech.homework.lesson6.common.StorageClient
import ru.tinkoff.fintech.homework.lesson6.model.Cake
import ru.tinkoff.fintech.homework.lesson6.model.Order
import java.lang.IllegalArgumentException

@Service
class OrderService(private val orderDao: OrderDao, private val storageClient: StorageClient) {
    fun addOrder(name: String, count: Int): Order {
        require (storageClient.consistCakeType(name)) { throw IllegalArgumentException("Не удалось добавить заказ с несуществующим тортом \"$name\"") }

        val orderId = orderDao.addOrder(storageClient.getCake(name), count)


        return getOrder(orderId)
    }

    fun getOrder(orderId: Int): Order = orderDao.getOrder(orderId)

    fun completeOrder(orderId: Int) {
        val order = getOrder(orderId)
        if (order.cake.count > storageClient.getCake(order.cake.name).count) {
            throw IllegalArgumentException("Заказ нельзя выполнить из-за большого кол-ва тортов")
        }
        orderDao.completeOrder(orderId)
        storageClient.updateCakesParams(order.cake.name, order.cake.cost, -order.cake.count)
    }
}
