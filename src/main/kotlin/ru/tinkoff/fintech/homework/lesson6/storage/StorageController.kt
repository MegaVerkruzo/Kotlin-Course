package ru.tinkoff.fintech.homework.lesson6.storage

import org.springframework.web.bind.annotation.*
import ru.tinkoff.fintech.homework.lesson6.model.Cake

@RestController
@RequestMapping("/storage/cake")
class StorageController(private val storageService: StorageService) {
    @GetMapping
    fun getCakesList(): List<Cake> = storageService.getCakesList()

    @PutMapping
    fun addCakes(@RequestBody cake: Cake) {
        storageService.addCakes(cake)
    }

    @PatchMapping
    fun updateCakeParams(@RequestParam name: String, @RequestParam  cost: Double?, @RequestParam count: Int?) {
        storageService.updateCakeParams(name, cost, count)
    }
}
