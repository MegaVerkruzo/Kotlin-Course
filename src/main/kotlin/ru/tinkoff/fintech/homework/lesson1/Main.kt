package ru.tinkoff.fintech.homework.lesson1

fun main(args: Array<String>) {
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
    for (i in 1..10) {
        cat.run()
    }
}