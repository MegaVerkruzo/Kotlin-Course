package ru.tinkoff.fintech.homework.lesson1

class Cat(private val weight: Int) : Animal(weight) {
    private val costOfRunning: Int = 15

    override fun say() {
        "Meow".prettyPrint()
    }

    override fun run() {
        super.run(costOfRunning)
    }

    override fun getAnimalType(): String {
        return "Cat"
    }
}