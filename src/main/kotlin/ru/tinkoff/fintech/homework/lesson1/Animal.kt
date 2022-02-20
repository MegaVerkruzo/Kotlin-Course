package ru.tinkoff.fintech.homework.lesson1

import kotlin.math.min
import kotlin.math.max

/*
Energy - это кол-во оставшейся энергии у животного. Если она станет равна нулю, то он не сможет ни есть, ни бегать
Weight - это переменная, которая определяет насколько питательное животное
 */

abstract class Animal(private val weight: Int) {
    private var energy: Int = 100

    abstract fun say()

    abstract fun run()

    abstract fun getAnimalType(): String

    private fun animalCondition() {
        when (energy) {
            in 1..24 -> "It's energy is low: $energy"
            in 25..75 -> "It's energy is middle: $energy"
            in 76..99 -> "It's energy is high: $energy"
            else -> "It died"
        }.prettyPrint()
    }

    fun isAlive(): Boolean {
        return energy > 0
    }

    protected fun run(costOfRunning: Int) {
        if (energy > 0) energy = max(0, energy - costOfRunning)
        animalCondition()
    }

    private fun die() {
        energy = 0
        "It dead".prettyPrint()
    }

    fun eat(animal: Animal) {
        if (animal.isAlive()) {
            animal.die()
            if (energy > 0) energy = min(100, energy + animal.weight)
            animalCondition()
        } else {
            "It can't eat a non-existent animal".prettyPrint()
        }
    }

    protected fun String.prettyPrint() {
        println(getAnimalType())
        println(this)
        println()
    }
}