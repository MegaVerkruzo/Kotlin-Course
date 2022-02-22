package ru.tinkoff.fintech.homework.lesson1

import kotlin.math.max
import kotlin.random.Random

/*
distance - расстояние между нападющим и жертвой
 */

class Hunting {
    private val hunter: Animal
    private val victim: Animal
    private var distance: Int = 1

    constructor(hunter: Animal, victim: Animal) {
        this.hunter = hunter
        this.victim = victim
    }

    private fun kill() {
        victim.die()
        hunter.addEnergy(victim.getWeight())
    }

    fun huntersRun() {
        hunter.run()
        distance = max(1, distance - 1)
    }

    fun victimsRun() {
        victim.run()
        distance++
    }

    fun huntersShoot(): Boolean {
        return if (victim.isAlive() && Random.nextDouble() < 1.0 / distance) {
            kill()
            true
        } else {
            false
        }
    }

    fun getVictim(): Animal = victim

    fun getHunter(): Animal = hunter

    fun getDistance(): Int = distance
}