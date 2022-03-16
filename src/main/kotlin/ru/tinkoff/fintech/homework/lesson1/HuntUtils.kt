package ru.tinkoff.fintech.homework.lesson1

import kotlin.random.Random

fun nextDouble() = Random.nextDouble()

private fun String.printInfo(animal: Animal) {
    println("___________________________________________")
    println("Now ${animal.type} has ${animal.energy} energy because $this")
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
        println("${animal.type}: ${animal.say()}")
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
    if (hunting.hunter.isAlive()) {
        hunting.huntersRun()
        println("___________________________________________")
        println("distance between hunter and victim: ${hunting.distance}")
    } else {
        "hunter has died".printInfo(hunting.hunter)
    }
}

fun victimRun(hunting: Hunting) {
    if (hunting.victim.isAlive()) {
        hunting.victimsRun()
        println("___________________________________________")
        println("distance between hunter and victim: ${hunting.distance}")
    } else {
        "victim has died".printInfo(hunting.victim)
    }
}

fun shoot(hunting: Hunting) {
    if (!hunting.hunter.isAlive()) {
        println("___________________________________________")
        println("Hunter has died")
        return
    }
    if (!hunting.victim.isAlive()) {
        println("___________________________________________")
        println("Victim has died")
        return
    }
    if (hunting.huntersShoot()) {
        "it killed victim".printInfo(hunting.hunter)
    } else {
        "it missed".printInfo(hunting.hunter)
    }
}
