package ru.tinkoff.fintech.homework.lesson6.storage

import org.springframework.web.bind.annotation.*
import ru.tinkoff.fintech.homework.lesson6.model.Order

@RestController
@RequestMapping("/order")
class OrderController (private val storage: Storage){
    @PostMapping("/add")
    fun addOrder(@RequestParam name: String, @RequestParam count: Int): Order =
        storage.addOrder(name, count)

    @GetMapping("/{orderId}")
    fun getOrder(@PathVariable orderId: Int): Order =
        storage.getOrder(orderId)

    @PatchMapping("/{orderId}/complete")
    fun doneOrder(@PathVariable orderId: Int) {
        storage.completeOrder(orderId)
    }
}
