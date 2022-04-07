package ru.tinkoff.fintech.homework.lesson6.store

import org.springframework.web.bind.annotation.*
import ru.tinkoff.fintech.homework.lesson6.model.Cake
import ru.tinkoff.fintech.homework.lesson6.model.Order

@RestController
@RequestMapping("/store")
class StoreController(private val store: Store) {
    @GetMapping("/list")
    fun getCakesList(): List<Cake> =
        store.getCakesList()

    @PostMapping("/buy")
    fun buyCakes(@RequestParam name: String, @RequestParam count: Int): Order =
        store.buyCakes(name, count)
}
