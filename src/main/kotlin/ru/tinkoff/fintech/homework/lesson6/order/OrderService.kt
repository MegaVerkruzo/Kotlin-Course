package ru.tinkoff.fintech.homework.lesson6.order

import org.springframework.stereotype.Service
import ru.tinkoff.fintech.homework.lesson6.model.Cake
import ru.tinkoff.fintech.homework.lesson6.model.Order
import java.lang.IllegalArgumentException

@Service
class OrderService(private val orderDao: OrderDao, private val storageClient: StorageClient) {

    fun addOrder(name: String, count: Int): Order {
        try {
            val orderId = orderDao.addOrder(getCake(name), count)
            return getOrder(orderId)
        } catch (e: IllegalArgumentException) {
            throw IllegalArgumentException("Не удалось добавить заказ с несуществующим тортом \"$name\"")
        }
    }

    fun getCake(name: String): Cake {
        if (!storageClient.consistCakeType(name)) throw IllegalArgumentException("Не существует торта с таким названием \"$name\"")

        return Cake(name, orderDao.getCake(name).cost, orderDao.getCake(name).count)
    }

    fun getOrder(orderId: Int): Order = orderDao.getOrder(orderId)

    fun completeOrder(orderId: Int) {
        val order = getOrder(orderId)
        if (order.cake.count > orderDao.getCake(order.cake.name).count) {
            throw IllegalArgumentException("Заказ нельзя выполнить из-за большого кол-ва тортов")
        }
        orderDao.completeOrder(orderId)
        updateCakeParams(order.cake.name, null, -order.cake.count)
    }
}
