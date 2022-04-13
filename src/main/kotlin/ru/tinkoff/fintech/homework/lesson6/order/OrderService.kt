package ru.tinkoff.fintech.homework.lesson6.order

import org.springframework.stereotype.Service
import ru.tinkoff.fintech.homework.lesson6.common.StorageClient
import ru.tinkoff.fintech.homework.lesson6.common.model.Order

@Service
class OrderService(private val orderDao: OrderDao, private val storageClient: StorageClient) {

    fun addOrder(name: String, count: Int): Int {
        val cake = storageClient.getCake(name)
        requireNotNull(cake) { "Не удалось добавить заказ с несуществующим тортом \"$name\"" }
        val order = Order(0, cake.copy(count = count))
        return orderDao.addOrder(order)
    }

    fun getOrder(orderId: Int): Order? = orderDao.getOrder(orderId)

    fun completedOrder(orderId: Int): Order {
        val order = getOrder(orderId)
        requireNotNull(order) { "Попытка обращения к несуществующему заказу \"$orderId\"" }
        val cake = order.cake
        storageClient.updateCake(cake.name, cake.cost, -cake.count)
        return orderDao.completedOrder(orderId)
    }
}
