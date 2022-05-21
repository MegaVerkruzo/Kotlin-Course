package ru.tinkoff.fintech.homework.lesson6.storage

import org.springframework.web.bind.annotation.*
import ru.tinkoff.fintech.homework.lesson6.common.model.Cake

@RestController
@RequestMapping("/storage/cake")
class StorageController(private val storageService: StorageService) {

    @GetMapping("/list")
    fun getCakes(): Set<Cake> = storageService.getCakes()

    @GetMapping
    fun getCake(@RequestParam name: String): Cake? =
        storageService.getCake(name)

    @PatchMapping
    fun updateCake(@RequestParam name: String, @RequestParam  cost: Double? = null, @RequestParam count: Int? = null): Cake =
        storageService.updateCake(name, cost, count)
}
