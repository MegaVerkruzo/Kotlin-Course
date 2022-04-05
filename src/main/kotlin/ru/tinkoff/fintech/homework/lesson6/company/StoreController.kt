package ru.tinkoff.fintech.homework.lesson6.company

import org.springframework.web.bind.annotation.*
import ru.tinkoff.fintech.homework.lesson6.company.model.Cake
import ru.tinkoff.fintech.homework.lesson6.company.model.OrderedCake
import ru.tinkoff.fintech.homework.lesson6.company.service.Store

@RestController
@RequestMapping("/store")
class StoreController(private val store: Store) {
    @GetMapping("/list")
    fun getCakesList(): Map<Cake, Int> =
        store.getCakesList()

//    @GetMapping("/list")
//    fun getCakeList(): List<Cake> =
//        confectionery.getCakesList()
//
//    @GetMapping("/{cakeId}")
//    fun getCake(@PathVariable name: String): Cake =
//        confectionery.getCake(name)
//
//    @PostMapping("/buy")
//    fun buyCake(@RequestParam name: String): OrderedCake =
//        confectionery.buyCake(name)!!

//    @GetMapping("/check")
//    fun check(@RequestParam name: String): String =
//        confectionery.check(name);

}