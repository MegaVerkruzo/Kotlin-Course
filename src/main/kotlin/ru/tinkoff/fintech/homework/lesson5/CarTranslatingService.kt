package ru.tinkoff.fintech.homework.lesson5

class CarTranslatingService(
    val brandOnEnglish: Map<String, String>,
    val bodyOnEnglish: Map<String, String>,
    val modelOnEnglish: Map<String, String>,
    val exchangeRate: Double = 100.0
) {
    fun translateCarToEnglish(car: Car): Car {
        if (exchangeRate <= 0) error("Курс должен быть положительным")

        return Car(
            modelOnEnglish.getValue(car.model),
            brandOnEnglish.getValue(car.brand),
            bodyOnEnglish.getValue(car.body),
            car.cost / exchangeRate,
            car.gasolineConsumption
        )
    }
}
