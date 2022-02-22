package ru.tinkoff.fintech.homework.lesson1

import kotlin.random.Random

class Hunter: Animal {
    constructor(weight: Int, costOfRunning: Int): super(weight, costOfRunning)

    override fun say(): String {
        return "I will shoot you!!!"
    }

    override fun getAnimalType(): String {
        return "Hunter"
    }

}