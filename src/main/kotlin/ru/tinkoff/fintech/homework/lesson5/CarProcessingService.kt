package ru.tinkoff.fintech.homework.lesson5

class CarProcessingService(val carTranslatingService: CarTranslatingService) {
    fun translateToEnglishSorted(cars: List<Car>) = cars
        .asSequence()
        .map { carTranslatingService.translateCarToEnglish(it) }
        .sortedBy { it.cost }
        .toList()

    fun groupByBrand(cars: List<Car>) = cars.groupBy { it.brand }

    fun groupByBody(cars: List<Car>) = cars.groupBy { it.body }

    fun suitableList(cars: List<Car>, predicate: (Car) -> Boolean) = cars
        .asSequence()
        .filter { predicate(it) }
        .map { carTranslatingService.translateCarToEnglish(it) }
        .take(3)
        .toList()
}
