package ru.tinkoff.fintech.homework.lesson1

class Cat(weight: Int, costOfRunning: Int) : Animal(weight, costOfRunning, "Cat") {
    override fun say(): String {
        return "Meow"
    }
}