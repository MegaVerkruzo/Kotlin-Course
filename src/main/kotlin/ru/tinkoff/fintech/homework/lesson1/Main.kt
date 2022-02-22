package ru.tinkoff.fintech.homework.lesson1

fun String.printInfo(animal: Animal) {
    println("___________________________________________")
    println("Now ${animal.getAnimalType()} has ${animal.getEnergy()} energy because $this")
}

fun die(animal: Animal) {
    animal.die()
    "it died".printInfo(animal)
}

fun say(animal: Animal) {
    if (!animal.isAlive()) {
        "it has died".printInfo(animal)
    } else {
        println("___________________________________________")
        println("${animal.getAnimalType()}: ${animal.say()}")
    }
}

fun run(animal: Animal) {
    if (!animal.isAlive()) {
        "it has dead".printInfo(animal)
    } else {
        animal.run()
        "it ran".printInfo(animal)
    }
}

fun main() {
    val cat = Cat(9, 32)
    say(cat)
    for (i in 0..4) {
        run(cat)
    }
    say(cat)

    val chicken = Chicken(3, 6)
    say(chicken)
    die(chicken)

    val victim = Chicken(4, 14)
    val hunter = Hunter(60, 40)

    say(hunter)

}