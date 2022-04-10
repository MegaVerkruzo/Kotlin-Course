package ru.tinkoff.fintech.homework.lesson6.storage

import org.springframework.web.bind.annotation.*
import ru.tinkoff.fintech.homework.lesson6.model.Cake

@RestController
@RequestMapping("/storage/cake")
class StorageController(private val storageService: StorageService) {
    @GetMapping("/list")
    fun getCakesList(): Set<Cake> = storageService.getCakesList()

    @GetMapping("/consist")
    fun consistCakeType(@RequestParam name: String): Boolean =
        storageService.consistCakes(name, 0)

    @GetMapping("/get")
    fun getCakes(@RequestParam name: String): Cake =
        storageService.getCake(name)

    @PutMapping
    fun addCakes(@RequestBody cake: Cake) {
        storageService.addCakes(cake)
    }

    @PatchMapping
    fun updateCakeParams(@RequestParam name: String, @RequestParam  cost: Double?, @RequestParam count: Int?): Cake =
        storageService.updateCakesParams(name, cost, count)
}
