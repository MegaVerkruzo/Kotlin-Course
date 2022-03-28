package ru.tinkoff.fintech.homework.lesson5

class Service(val exchangeRate: Int = 100, val brandOnEnglish: Map<String, String>, val bodyOnEnglish: Map<String, String>, val modelOnEnglish: Map<String, String>) {
    fun translateToEnglishSorted(cars: List<Car>) = cars
        .asSequence()
        .map { translateCarToEnglish(it) }
        .sortedBy { it.cost }
        .toList()

    fun groupByBrand(cars: List<Car>) = cars.groupBy { it.brand }

    fun groupByBody(cars: List<Car>) = cars.groupBy { it.body }

    fun suitableList(cars: List<Car>, predicate: (Car) -> Boolean) = cars
        .asSequence()
        .filter { predicate(it) }
        .map { translateCarToEnglish(it) }
        .take(3)
        .toList()

    private fun brandToEnglish(name: String) = brandOnEnglish.getValue(name)

    private fun bodyToEnglish(name: String) = bodyOnEnglish.getValue(name)

    private fun modelToEnglish(name: String) = modelOnEnglish.getValue(name)

    private fun costToDollars(cost: Double): Double {
        return cost / exchangeRate
    }

    private fun translateCarToEnglish(car: Car): Car {
        return Car(
            modelToEnglish(car.model),
            brandToEnglish(car.brand),
            bodyToEnglish(car.body),
            costToDollars(car.cost),
            car.gasolineConsumption
        )
    }
}
