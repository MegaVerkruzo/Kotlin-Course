package ru.tinkoff.fintech.homework.lesson6.company.store

import org.springframework.web.bind.annotation.*
import ru.tinkoff.fintech.homework.lesson6.company.model.Cake
import ru.tinkoff.fintech.homework.lesson6.company.model.Order

@RestController
@RequestMapping("/store")
class StoreController(private val store: Store) {
    @GetMapping("/list")
    fun getCakesList(): Map<Cake, Int> =
        store.getCakesList()

    @GetMapping("/buy")
    fun buyCakes(@RequestParam name: String, @RequestParam count: Int): Order =
        store.buyCakes(name, count)
}