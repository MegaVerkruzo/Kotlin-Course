package ru.tinkoff.fintech.homework.lesson6.order

import org.springframework.web.bind.annotation.*
import ru.tinkoff.fintech.homework.lesson6.model.Cake
import ru.tinkoff.fintech.homework.lesson6.model.Order

@RestController
@RequestMapping("/order")
class OrderController(private val orderService: OrderService) {
    @PostMapping("/add")
    fun addOrder(@RequestParam name: String, @RequestParam count: Int): Order =
        orderService.addOrder(name, count)

    @GetMapping("/{orderId}")
    fun getOrder(@PathVariable orderId: Int): Order =
        orderService.getOrder(orderId)

    @PostMapping("/{orderId}/complete")
    fun completeOrder(@PathVariable orderId: Int): Cake =
        orderService.completeOrder(orderId)
}
