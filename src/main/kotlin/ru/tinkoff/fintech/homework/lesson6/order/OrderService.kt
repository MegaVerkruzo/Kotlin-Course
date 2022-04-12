package ru.tinkoff.fintech.homework.lesson6.order

import org.springframework.stereotype.Service
import ru.tinkoff.fintech.homework.lesson6.common.StorageClient
import ru.tinkoff.fintech.homework.lesson6.common.model.Cake
import ru.tinkoff.fintech.homework.lesson6.common.model.Order
import java.util.Objects.requireNonNull

@Service
class OrderService(private val orderDao: OrderDao, private val storageClient: StorageClient) {

    fun addOrder(name: String, count: Int): Order {
        val cake: Cake? = storageClient.getCake(name)
        requireNonNull(cake) { "Не удалось добавить заказ с несуществующим тортом \"$name\"" }
        val order = Order(orderDao.orderId, cake!!.copy(count = count), false)
        orderDao.addOrder(order)
        return order
    }

    fun getOrder(orderId: Int): Order? = orderDao.getOrder(orderId)

    fun completeOrder(orderId: Int): Cake {
        val order: Order? = getOrder(orderId)
        requireNonNull(order) { "Попытка обращения к несуществующему заказу \"$orderId\"" }
        require(!order!!.completed) { "Попытка выполнения уже выполненнного заказа \"$orderId\"" }
        val cake: Cake = order.cake
        val result = storageClient.updateCake(cake.name, cake.cost, -cake.count)
        orderDao.completeOrder(orderId)
        return result
    }
}
