package ru.tinkoff.fintech.homework.lesson6.store

import org.springframework.web.bind.annotation.*
import ru.tinkoff.fintech.homework.lesson6.common.StorageClient
import ru.tinkoff.fintech.homework.lesson6.common.model.Cake
import ru.tinkoff.fintech.homework.lesson6.common.model.Order

@RestController
@RequestMapping("/store/cake")
class StoreController(private val storageClient: StorageClient, private val orderClient: OrderClient) {

    @GetMapping("/list")
    fun getCakes(): Set<Cake> =
        storageClient.getCakes()

    @PostMapping("/add-order")
    fun addOrder(@RequestParam name: String, @RequestParam count: Int): Int =
        orderClient.addOrder(name, count)
}
