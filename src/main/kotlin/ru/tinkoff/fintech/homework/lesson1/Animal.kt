package ru.tinkoff.fintech.homework.lesson1

import kotlin.math.min
import kotlin.math.max

/*
Energy - это кол-во оставшейся энергии у животного. Если она станет равна нулю, то он не сможет ни есть, ни бегать
Weight - это переменная, которая определяет насколько питательное животное
 */

abstract class Animal {
    private val weight: Int
    private val costOfRunning: Int
    private var energy: Int = 100

    constructor(weight: Int, costOfRunning: Int) {
        this.weight = weight
        this.costOfRunning = costOfRunning
    }

    abstract fun say(): String

    abstract fun getAnimalType(): String

    fun run() {
        if (energy > 0) energy = max(0, energy - costOfRunning)
    }

    fun energy(): Int = energy

    fun isAlive(): Boolean = energy > 0

    fun die() {
        energy = 0
    }
}