package ru.tinkoff.fintech.homework.lesson1

import kotlin.random.Random

class Hunter(private val victim: Animal) {
    private var victimIsCaught: Boolean = false

    fun isVictimCaught() {
        if (victimIsCaught) "Victim is caught".prettyPrint()
        else "Victim isn't caught".prettyPrint()
    }

    fun tryToCaught() {
        if (victimIsCaught) {
            isVictimCaught()
            return
        }
        if (Random.nextDouble() < 1.0 / victim.getDistance()) {
            victimIsCaught = true
            victim.die()
            "Successful try".prettyPrint()
        }
        else "Unsuccessful try".prettyPrint()
    }

    private fun String.prettyPrint() {
        println("Hunter")
        println(this)
        println()
    }
}