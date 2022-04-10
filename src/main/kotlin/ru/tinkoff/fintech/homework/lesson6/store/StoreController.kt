package ru.tinkoff.fintech.homework.lesson6.store

import org.springframework.web.bind.annotation.*
import ru.tinkoff.fintech.homework.lesson6.model.Cake
import ru.tinkoff.fintech.homework.lesson6.model.Order

@RestController
@RequestMapping("/store/cake")
class StoreController(private val storageClient: StorageClient) {
    @GetMapping("/list")
    fun getCakesList(): MutableCollection<Cake> =
        storageClient.getCakesList()

    @PostMapping("/buy")
    fun buyCakes(@RequestParam name: String, @RequestParam count: Int): Order =
        storageClient.buyCakes(name, count)
}
