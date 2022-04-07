package ru.tinkoff.fintech.homework.lesson6.storage

import org.springframework.web.bind.annotation.*
import ru.tinkoff.fintech.homework.lesson6.model.Cake

@RestController
@RequestMapping("/storage/cake")
class StorageController(private val storage: Storage) {
    @GetMapping("/list")
    fun getCakesList(): List<Cake> = storage.getCakesList()

    @GetMapping("/consist")
    fun consistCake(@RequestParam name: String): Boolean {
        return storage.consistCakes(name, 1)
    }

    @PutMapping("/change-count-with-new-cost")
    fun addCakes(@RequestParam name: String, @RequestParam cost: Double, @RequestParam count: Int) {
        storage.addCakes(name, cost, count)
    }

    @PatchMapping("/change-count-without-cost")
    fun addCakes(@RequestParam name: String, @RequestParam count: Int) {
        storage.changeCakesCount(name, count)
    }
}
