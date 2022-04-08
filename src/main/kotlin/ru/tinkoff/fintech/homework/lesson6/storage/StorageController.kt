package ru.tinkoff.fintech.homework.lesson6.storage

import org.springframework.web.bind.annotation.*
import ru.tinkoff.fintech.homework.lesson6.model.Cake

@RestController
@RequestMapping("/storage/cake")
class StorageController(private val storageService: StorageService) {
    @GetMapping("/list")
    fun getCakesList(): List<Cake> = storageService.getCakesList()

    @GetMapping("/consist")
    fun consistCake(@RequestParam name: String): Boolean {
        return storageService.consistCakes(name, 1)
    }

    @PutMapping("/change-count-with-new-cost")
    fun addCakes(@RequestParam name: String, @RequestParam cost: Double, @RequestParam count: Int) {
        storageService.addCakes(name, cost, count)
    }

    @PatchMapping("/change-count-without-cost")
    fun addCakes(@RequestParam name: String, @RequestParam count: Int) {
        storageService.changeCakesCount(name, count)
    }
}
