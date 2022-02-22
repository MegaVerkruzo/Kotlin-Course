package ru.tinkoff.fintech.homework.lesson1

class Cat : Animal {
    constructor(weight: Int, costOfRunning: Int) : super(weight, costOfRunning)

    override fun say(): String {
        return "Meow"
    }

    override fun getAnimalType(): String {
        return "Cat"
    }
}