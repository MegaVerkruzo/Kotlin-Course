package ru.tinkoff.fintech.homework.lesson6.company

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.tinkoff.fintech.homework.lesson6.company.model.Cake
import ru.tinkoff.fintech.homework.lesson6.company.service.Store

@RestController
@RequestMapping("/cake")
class CakeController(private val confectionery: Store) {
    @GetMapping("/list")
    fun getCakeList(): Set<Cake> =
        confectionery.getCakesList()

    @GetMapping("/{cakeId}")
    fun getCake(@PathVariable cakeId: Int): Cake =
        confectionery.getCake(cakeId)

}