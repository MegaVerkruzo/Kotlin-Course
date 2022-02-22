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

fun hunterRun(hunting: Hunting) {
    if (hunting.getHunter().isAlive()) {
        run(hunting.getHunter())
        hunting.reduceDistance()
        println("distance between hunter and victim: ${hunting.getDistance()}")
    } else {
        "hunter has died".printInfo(hunting.getHunter())
    }
}

fun victimRun(hunting: Hunting) {
    if (hunting.getVictim().isAlive()) {
        run(hunting.getVictim())
        hunting.increaseDistance()
        println("distance between hunter and victim: ${hunting.getDistance()}")
    } else {
        "victim has died".printInfo(hunting.getVictim())
    }
}

fun shoot(hunting: Hunting) {
    if (!hunting.getHunter().isAlive()) {
        println("hunter has died")
        return
    }
    if (!hunting.getVictim().isAlive()) {
        println("victim has died")
        return
    }
    if (hunting.huntersShoot()) {
        "it killed victim".printInfo(hunting.getHunter())
    } else {
        "it missed".printInfo(hunting.getHunter())
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

    val hunting = Hunting(hunter, victim)
    for (i in 1..3) {
        victimRun(hunting)
    }
    hunterRun(hunting)
    for (i in 1..5) {
        shoot(hunting)
    }
}