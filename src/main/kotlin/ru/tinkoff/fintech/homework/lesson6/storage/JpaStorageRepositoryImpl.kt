//package ru.tinkoff.fintech.homework.lesson6.storage
//
//import ru.tinkoff.fintech.homework.lesson6.common.model.Cake
//
//class JpaStorageRepositoryImpl(private val repository: JpaStorageDao) : StorageDao  {
//    override fun getCakes(): Set<Cake> = repository.findAll().toSet()
//
//    override fun getCake(name: String): Cake? = repository.findByName(name)
//
//    override fun updateCake(cake: Cake): Cake {
//        TODO("Not yet implemented")
//    }
//}