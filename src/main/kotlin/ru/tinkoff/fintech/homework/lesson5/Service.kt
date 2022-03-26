package ru.tinkoff.fintech.homework.lesson5

class Service(val exchangeRate: Int = 100) {
    fun translateToEnglishSorted(cars: List<Car>) = translateToEnglishSortedWithSequence(cars).toList()

    fun groupByBrand(cars: List<Car>) = cars.groupBy { it.brand }

    fun groupByBody(cars: List<Car>) = cars.groupBy { it.body }

    fun suitableList(cars: List<Car>, func: (Car) -> Boolean): List<Car> {
        return translateToEnglishSortedWithSequence(cars.filter { func(it) }).take(3).toList()
    }

    private fun translateToEnglishSortedWithSequence(cars: List<Car>) = cars.asSequence()
        .map { translateCarToEnglish(it) }
        .sortedBy { it.cost }

    private fun brandToEnglish(name: String) = brandOnEnglish[name]!!

    private fun bodyToEnglish(name: String) = bodyOnEnglish[name]!!

    private fun modelToEnglish(name: String): String {
        return when (name) {
            "730лд иксДрайв" -> "730Ld xDrive"
            "икс6 М50д М спешиал" -> "X6 M50d M Special"
            "911 каррера" -> "911 Carrera"
            "Веста" -> "Vesta"
            "М4" -> "M4"
            "ТТС" -> "TTS"
            else -> "UNKNOWN"
        }
    }

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