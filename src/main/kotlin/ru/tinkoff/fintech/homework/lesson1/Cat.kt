package ru.tinkoff.fintech.homework.lesson1

import kotlin.math.min
import kotlin.math.max

class Cat(private val weight: Int): Animal(weight) {
    private val costOfRunning: Int = 15

    override fun say(): String {
        return "Meow\n"
    }

    override fun run(): String {
        return super.run(costOfRunning)
    }

    override fun die(): String {
        return super.die("Cat")
    }


}