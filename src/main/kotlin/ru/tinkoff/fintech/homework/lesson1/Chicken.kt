package ru.tinkoff.fintech.homework.lesson1

import kotlin.math.min

class Chicken(private val weight: Int) : Animal(weight) {
    private val costOfRunning: Int = 5

    override fun say(): String {
        return "Cock-A-Doodle-Doo"
    }

    fun run(): String {
        return super.run(costOfRunning)
    }
}