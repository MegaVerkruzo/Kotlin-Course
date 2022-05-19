package ru.tinkoff.fintech.homework.lesson1

class Hunter(weight: Int, costOfRunning: Int) : Animal(weight, costOfRunning, "Hunter") {
    override fun say(): String = "I will shoot you!!!"
}