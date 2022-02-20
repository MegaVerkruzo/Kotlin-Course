package ru.tinkoff.fintech.homework.lesson1

class Chicken(private val weight: Int) : Animal(weight) {
    private val costOfRunning: Int = 5

    override fun say() {
        "Cock-A-Doodle-Doo\n".prettyPrint()
    }

    override fun run() {
        super.run(costOfRunning)
    }

    override fun getAnimalType(): String {
        return "Chicken"
    }
}