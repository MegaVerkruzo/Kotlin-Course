package ru.tinkoff.fintech.homework.lesson6.company

import org.springframework.web.bind.annotation.*
import ru.tinkoff.fintech.homework.lesson6.company.model.Cake
import ru.tinkoff.fintech.homework.lesson6.company.service.Storage

@RestController
@RequestMapping("/storage")
class StorageController(private val storage: Storage) {
    @GetMapping("/list")
    fun getCakesList(): Map<Cake, Int> =
        storage.getCakesList()

    @GetMapping("/consist-cake")
    fun consistCake(@RequestParam name: String): Boolean {
        return storage.consistCake(name)
    }

    @PutMapping("/add-cakes-with-cost")
    fun addCakes(@RequestParam name: String, @RequestParam cost: Double, @RequestParam count: Int): String {
        storage.addCakes(name, cost, count)
        return "Successful operation"
    }

    @PutMapping("/add-cakes")
    fun addCakes(@RequestParam name: String, @RequestParam count: Int): String {
        storage.changeCakesCount(name, count)
        return "Successful operation"
    }
    
    @PutMapping("/delete-cakes")
    fun deleteCakes(@RequestParam name: String, @RequestParam count: Int): String {
        storage.deleteCakes(name, count)
        return "Successful operation"
    }

}