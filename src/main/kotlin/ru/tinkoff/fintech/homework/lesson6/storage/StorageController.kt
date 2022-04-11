package ru.tinkoff.fintech.homework.lesson6.storage

import org.springframework.web.bind.annotation.*
import ru.tinkoff.fintech.homework.lesson6.common.model.Cake

@RestController
@RequestMapping("/storage/cake")
class StorageController(private val storageService: StorageService) {

    @GetMapping("/list")
    fun getCakes(): Set<Cake> = storageService.getCakes()

    @GetMapping("/contain")
    fun containCakeType(@RequestParam name: String): Boolean =
        storageService.containCake(name, 0)

    @GetMapping("/get")
    fun getCake(@RequestParam name: String): Cake =
        storageService.getCake(name)

    @PatchMapping
    fun updateCakeParams(@RequestParam name: String, @RequestParam  cost: Double?, @RequestParam count: Int?): Cake =
        storageService.updateCakeParams(name, cost, count)
}
