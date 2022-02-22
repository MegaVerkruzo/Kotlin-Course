package ru.tinkoff.fintech.homework.lesson1

import kotlin.math.max
import kotlin.math.min

/*
Energy - это кол-во оставшейся энергии у животного. Если она станет равна нулю, то он умрёт
Weight - это переменная, которая определяет насколько питательное животное
 */

abstract class Animal(val weight: Int, private val costOfRunning: Int, val type: String, var energy: Int = 100) {
    abstract fun say(): String

    fun run() {
        if (isAlive()) energy = max(0, energy - costOfRunning)
    }

    fun die() {
        energy = 0
    }

    fun isAlive(): Boolean = energy > 0

    fun addEnergy(additiveEnergy: Int) {
        if (isAlive()) this.energy = min(100, this.energy + additiveEnergy)
    }
}