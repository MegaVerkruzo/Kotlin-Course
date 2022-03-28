package ru.tinkoff.fintech.homework.lesson5

class CarTranslatingService(val exchangeRate: Double = 100.0, val brandOnEnglish: Map<String, String>, val bodyOnEnglish: Map<String, String>, val modelOnEnglish: Map<String, String>) {
    fun translateCarToEnglish(car: Car): Car {
        assert(exchangeRate > 0, error("Неверный курс")

        return Car(
            modelOnEnglish.getValue(car.model),
            brandOnEnglish.getValue(car.brand),
            bodyOnEnglish.getValue(car.body),
            car.cost / exchangeRate,
            car.gasolineConsumption
        )
    }
}
