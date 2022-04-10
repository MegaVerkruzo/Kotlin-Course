package ru.tinkoff.fintech.homework.lesson6.store

import org.springframework.web.bind.annotation.*
import ru.tinkoff.fintech.homework.lesson6.common.StorageClient
import ru.tinkoff.fintech.homework.lesson6.model.Cake
import ru.tinkoff.fintech.homework.lesson6.model.Order

@RestController
@RequestMapping("/store/cake")
class StoreController(private val storageClient: StorageClient, private val orderClient: OrderClient) {
    @GetMapping("/list")
    fun getCakesList(): Set<Cake> =
        storageClient.getCakesList()

    @PostMapping("/add-order")
    fun addCakesOrder(@RequestParam name: String, @RequestParam count: Int): Order =
        orderClient.addCakesOrder(name, count)
}
