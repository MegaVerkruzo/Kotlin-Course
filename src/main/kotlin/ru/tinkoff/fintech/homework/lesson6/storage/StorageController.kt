package ru.tinkoff.fintech.homework.lesson6.storage

import org.springframework.web.bind.annotation.*
import ru.tinkoff.fintech.homework.lesson6.common.model.Cake

@RestController
@RequestMapping("/storage/cake")
class StorageController(private val storageService: StorageService) {
    @GetMapping("/list")
    fun getCakeList(): Set<Cake> = storageService.getCakeList()

    @GetMapping("/contain")
    fun containCakeType(@RequestParam name: String): Boolean =
        storageService.containCake(name, 0)

    @GetMapping("/get")
    fun getCake(@RequestParam name: String): Cake =
        storageService.getCake(name)

    @PutMapping
    fun addCake(@RequestBody cake: Cake) {
        storageService.addCake(cake)
    }

    @PatchMapping
    fun updateCakeParams(@RequestParam name: String, @RequestParam  cost: Double?, @RequestParam count: Int?): Cake =
        storageService.updateCakeParams(name, cost, count)
}
