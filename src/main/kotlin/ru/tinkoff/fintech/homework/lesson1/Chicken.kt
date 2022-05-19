package ru.tinkoff.fintech.homework.lesson1

class Chicken(weight: Int, costOfRunning: Int) : Animal(weight, costOfRunning, "Chicken") {
    override fun say(): String = "Cock-A-Doodle-Doo"
}