package ru.tinkoff.fintech.homework.lesson5

class Service(val exchangeRate: Int = 100) {
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

val brandOnEnglish: Map<String, String> = mapOf(
    "АУДИ" to "AUDI",
    "БМВ" to "BMW",
    "ФОРД" to "FORD",
    "ХОНДА" to "HONDA",
    "ХЁНДАЙ" to "HYUNDAI",
    "КИА" to "KIA",
    "ЛАДА" to "LADA",
    "МАЗДА" to "MAZDA",
    "ФОЛКСВАГЕН" to "VOLKSWAGEN",
    "КАДИЛАК" to "CADILLAC",
    "ТЕСЛА" to "TESLA",
    "ПОРШ" to "PORSCHE"
)

val bodyOnEnglish: Map<String, String> = mapOf(
    "ХЕТЧБЕК" to "HATCHBACK",
    "СПОРТКАР" to "SPORTSCAR",
    "ЧЕТЫРЁХДВЕРНОЙСЕДАН" to "FOURDOORSEDAN",
    "ЛИМУЗИН" to "LIMOUSINE",
    "КАБРИОЛЕТ" to "CONVERTIBLE",
    "СЕДАН" to "HARDTOP",
    "ФУРГОН" to "VAN",
    "ПИКАП" to "PICKUPTRUCK"
)

val modelOnEnglish: Map<String, String> = mapOf(
    "730лд иксДрайв" to "730Ld xDrive",
    "икс6 М50д М спешиал" to "X6 M50d M Special",
    "911 каррера" to "911 Carrera",
    "Веста" to "Vesta",
    "М4" to "M4",
    "ТТС" to "TTS"
)
