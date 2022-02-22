package ru.tinkoff.fintech.homework.lesson1

class Hunter : Animal {
    constructor(weight: Int, costOfRunning: Int) : super(weight, costOfRunning)

    override fun say(): String {
        return "I will shoot you!!!"
    }

    override fun getAnimalType(): String {
        return "Hunter"
    }

}