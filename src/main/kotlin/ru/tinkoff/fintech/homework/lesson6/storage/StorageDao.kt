package ru.tinkoff.fintech.homework.lesson6.storage

import ru.tinkoff.fintech.homework.lesson6.common.model.Cake

interface StorageDao {

    fun getCakes(): Set<Cake>

    fun getCake(name: String): Cake?

    fun updateCake(cake: Cake): Cake
}