package ru.tinkoff.fintech.homework.lesson1

class Chicken : Animal {
    constructor(weight: Int, costOfRunning: Int) : super(weight, costOfRunning)

    override fun say(): String {
        return "Cock-A-Doodle-Doo"
    }

    override fun getAnimalType(): String {
        return "Chicken"
    }
}