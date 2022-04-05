package ru.tinkoff.fintech.homework.lesson6.company.storage

import org.springframework.web.bind.annotation.*
import ru.tinkoff.fintech.homework.lesson6.company.model.Cake
import ru.tinkoff.fintech.homework.lesson6.company.model.Order

@RestController
@RequestMapping("/storage")
class StorageController(private val storage: Storage) {
    @GetMapping("/list")
    fun getCakesList(): Map<String, Pair<Cake, Int>> = storage.getCakesList()

    @GetMapping("/consist-cake")
    fun consistCake(@RequestParam name: String): Boolean {
        return storage.consistCakes(name, 1)
    }

    @PostMapping("/add-cakes-with-cost")
    fun addCakes(@RequestParam name: String, @RequestParam cost: Double, @RequestParam count: Int) {
        storage.addCakes(name, cost, count)
    }

    @PostMapping("/add-cakes")
    fun addCakes(@RequestParam name: String, @RequestParam count: Int) {
        storage.changeCakesCount(name, count)
    }

    @PostMapping("/delete-cakes")
    fun deleteCakes(@RequestParam name: String, @RequestParam count: Int) {
        storage.deleteCakes(name, count)
    }

    @PostMapping("/add-order")
    fun addOrder(@RequestParam name: String, @RequestParam count: Int): Order =
        storage.addOrder(name, count)

    @GetMapping("/order/{orderId}")
    fun getOrder(@PathVariable orderId: Int): Order =
        storage.getOrder(orderId)

    @PostMapping("/order/{orderId}/done")
    fun doneOrder(@PathVariable orderId: Int) {
        storage.doneOrder(orderId)
    }
}
