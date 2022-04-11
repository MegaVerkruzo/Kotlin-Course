package ru.tinkoff.fintech.homework.lesson6.order

import org.springframework.stereotype.Service
import ru.tinkoff.fintech.homework.lesson6.common.StorageClient
import ru.tinkoff.fintech.homework.lesson6.common.model.Cake
import ru.tinkoff.fintech.homework.lesson6.common.model.Order
import java.lang.IllegalArgumentException

@Service
class OrderService(private val orderDao: OrderDao, private val storageClient: StorageClient) {
    fun addOrder(name: String, count: Int): Order {
        require(storageClient.containCakeType(name)) { throw IllegalArgumentException("Не удалось добавить заказ с несуществующим тортом \"$name\"") }
        val currentCake: Cake = storageClient.getCake(name)
        val orderId = orderDao.addOrder(currentCake, count)

        return getOrder(orderId)
    }

    fun getOrder(orderId: Int): Order = orderDao.getOrder(orderId)
        ?: throw IllegalArgumentException("Попытка обратиться к заказу под несуществующим номером $orderId")

    fun completeOrder(orderId: Int): Cake {
        val order = getOrder(orderId)
        if (order.cake.count > storageClient.getCake(order.cake.name).count) {
            throw IllegalArgumentException("Заказ нельзя выполнить из-за большого кол-ва тортов")
        }
        orderDao.completeOrder(orderId)
        val orderCake: Cake = order.cake
        return storageClient.updateCakeParams(orderCake.name, orderCake.cost, -orderCake.count)
    }
}
