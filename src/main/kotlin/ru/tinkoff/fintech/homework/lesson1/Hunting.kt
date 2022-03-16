package ru.tinkoff.fintech.homework.lesson1

import kotlin.math.max
import kotlin.random.Random

/*
distance - расстояние между нападющим и жертвой
 */

class Hunting(val hunter: Animal, val victim: Animal, var distance: Int = 1) {
    private fun kill() {
        victim.die()
        hunter.addEnergy(victim.weight)
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
        return if (hunter.isAlive() && victim.isAlive() && successfulHunting(this)) {
            kill()
            true
        } else {
            false
        }
    }
}