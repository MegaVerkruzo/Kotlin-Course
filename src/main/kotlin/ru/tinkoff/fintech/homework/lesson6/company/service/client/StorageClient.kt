package ru.tinkoff.fintech.homework.lesson6.company.service.client

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import ru.tinkoff.fintech.homework.lesson6.company.model.Cake
import ru.tinkoff.fintech.homework.lesson6.company.model.Order
import java.lang.IllegalArgumentException

@Service
class StorageClient(
    val restTemplate: RestTemplate,
    @Value("\${storage.list.address}") private val storageListClient: String
) {
    fun getCakesList(): MutableMap<Cake, Int> = data.toMutableMap()

    fun consistCakeType(name: String): Boolean = cakeCost.containsKey(name)

    fun consistCake(name: String): Boolean {
        require(cakeCost.containsKey(name) && data.containsKey(cakeCost[name])) { return false }

        return data[cakeCost[name]]!! > 0
    }

    fun changeCakesCount(name: String, count: Int) {
        require(cakeCost.containsKey(name) && data.containsKey(cakeCost[name])) { throw IllegalArgumentException("Такого типа торта нет на складе") }

        data[cakeCost[name]!!] = count + data[cakeCost[name]!!]!!
    }

    fun addOrUpdateCake(name: String, cost: Double) {
        var count = 0
        if (cakeCost.containsKey(name)) {
            require(data.containsKey(cakeCost[name])) { throw IllegalArgumentException() }

            count = data[cakeCost[name]]!!
            data.remove(cakeCost[name]!!)
        }
        cakeCost.remove(name)
        cakeCost[name] = Cake(name, cost)
        data[cakeCost[name]!!] = count
    }

    fun cakeCount(cake: Cake): Int = data[cake]!!

    fun getCake(name: String): Cake = cakeCost[name]!!

    fun getNumberOrder(): Int = orders.size

    fun getOrder(orderId: Int): Order = orders[orderId]

    fun doneOrder(orderId: Int) {
        require(!orders[orderId].packed) { throw IllegalArgumentException("Заказ уже был выполнен")}
        require(data[orders[orderId].cake]!! > orders[orderId].cakesCount) {
            throw IllegalArgumentException("Не хватает кол-во тортов на складе")
        }

        orders[orderId].packed = true
        data[orders[orderId].cake] = data[orders[orderId].cake]!! - orders[orderId].cakesCount
    }

    fun addOrder(name: String, count: Int): Order {
        orders.add(Order(getNumberOrder(), getCake(name), count, false))
        return orders[orders.size - 1]
    }

    private val cakeCost: MutableMap<String, Cake> = mutableMapOf("cesar" to Cake("cesar", 432.0))
    private val data: MutableMap<Cake, Int> = mutableMapOf(Cake("cesar", 432.0) to 20)
    private val orders: MutableList<Order> = mutableListOf()
}
