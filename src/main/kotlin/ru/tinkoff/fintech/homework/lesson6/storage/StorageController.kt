package ru.tinkoff.fintech.homework.lesson6.storage

import org.springframework.web.bind.annotation.*
import ru.tinkoff.fintech.homework.lesson6.model.Cake

@RestController
@RequestMapping("/storage/cake")
class StorageController(private val storageService: StorageService) {
    @GetMapping("/list")
    fun getCakesList(): List<Cake> = storageService.getCakesList()

    @PutMapping("/add")
    fun addCakes(@RequestBody cake: Cake) {
        storageService.addCakes(cake)
    }

    @PatchMapping("/update-count")
    fun addCakes(@RequestParam name: String, @RequestParam count: Int) {
        storageService.updateCakesCount(name, count)
    }
}
