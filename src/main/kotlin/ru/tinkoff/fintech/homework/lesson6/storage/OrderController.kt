package ru.tinkoff.fintech.homework.lesson6.storage

import org.springframework.web.bind.annotation.*
import ru.tinkoff.fintech.homework.lesson6.model.Order

@RestController
@RequestMapping("/order")
class OrderController (private val storageService: StorageService){
    @PostMapping("/add")
    fun addOrder(@RequestParam name: String, @RequestParam count: Int): Order =
        storageService.addOrder(name, count)

    @GetMapping("/{orderId}")
    fun getOrder(@PathVariable orderId: Int): Order =
        storageService.getOrder(orderId)

    @PostMapping("/{orderId}/complete")
    fun completeOrder(@PathVariable orderId: Int) {
        storageService.completeOrder(orderId)
    }
}
