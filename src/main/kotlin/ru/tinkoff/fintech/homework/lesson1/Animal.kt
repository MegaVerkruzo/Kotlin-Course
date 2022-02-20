package ru.tinkoff.fintech.homework.lesson1

import kotlin.math.min
import kotlin.math.max

/*
Energy - это кол-во оставшейся энергии у животного. Если она станет равна нулю, то он не сможет ни есть, ни бегать
Weight - это переменная, которая определяет насколько питательное животное
 */

abstract class Animal {
    private val weight: Int
    private var energy: Int = 100

    constructor(weight: Int) {
        this.weight = weight
    }

    abstract fun say(): String

    private fun animalCondition(): String {
        return when (energy) {
            in 1..24 -> "It's energy is low: $energy"
            in 25..75 -> "It's energy is middle: $energy"
            in 76..99 -> "It's energy is high: $energy"
            else -> "It died"
        }
    }

    protected fun eat(animal: Animal): String {
        if (energy > 0) energy = min(100, energy + animal.weight)
        return animalCondition()
    }

    protected fun run(costOfRunning: Int): String {
        if (energy > 0) energy = max(0, energy - costOfRunning)
        return animalCondition()
    }
}