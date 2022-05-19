package ru.tinkoff.fintech.homework.lesson1

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
    for (i in 1..3) {
        shoot(hunting)
    }
}