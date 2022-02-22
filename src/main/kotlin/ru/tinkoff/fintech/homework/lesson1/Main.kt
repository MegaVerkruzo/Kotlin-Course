package ru.tinkoff.fintech.homework.lesson1

fun String.print(animal: Animal) {
    println("___________________________________________")
    println("Now ${animal.getAnimalType()} has ${animal.getEnergy()} energy because $this")
}

fun run(animal: Animal) {
    if (animal.getEnergy() == 0) {
        "it dead".print(animal)
    } else {
        animal.run()
        "it ran".print(animal)
    }
}

fun main() {
    val cat = Cat(9, 32)
    run(cat)
    run(cat)
    run(cat)
    run(cat)
    run(cat)
}