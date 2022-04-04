package ru.tinkoff.fintech.homework.lesson6.company

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.tinkoff.fintech.homework.lesson6.company.model.Cake
import ru.tinkoff.fintech.homework.lesson6.company.service.Storage

@RestController
@RequestMapping("/storage")
class StorageController(private val storage: Storage) {
    @GetMapping("/list")
    fun getCakesList(): Map<Cake, Int> =
        storage.getCakesList()

    @GetMapping("/add")
    fun addCakes(@RequestParam name: String, @RequestParam cost: Double, @RequestParam count: Int) {
        storage.addCakes(name, cost, count)
    }
//    fun addCakes(@RequestParam name: String, @RequestParam count: Int) {
//        storage.addCakes(name, count)
//    }
}