package ru.tinkoff.fintech.homework.lesson1

import kotlin.random.Random.Default.nextDouble

fun main() {
    val cat = Cat(8)
    cat.say()
    cat.run()
    cat.run()

    val chicken = Chicken(3)
    chicken.say()
    chicken.run()
    chicken.run()

    cat.eat(chicken)
    chicken.run()

    cat.eat(chicken)

    val hunter = Hunter(cat)
    hunter.isVictimCaught()
    for (i in 1..4) {
        cat.run()
    }
    for (i in 1..10) {
        hunter.tryToCaught()
    }

}