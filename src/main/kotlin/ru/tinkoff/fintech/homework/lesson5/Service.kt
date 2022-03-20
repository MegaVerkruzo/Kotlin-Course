package ru.tinkoff.fintech.homework.lesson5

class Service {
    val exchangeRate: Int = 100

    fun onEnglish(cars: List<Car>): List<Car> {
        return cars
            .map {
                Car(
                    modelToEnglish(it.model),
                    brandToEnglish(it.brand),
                    bodyToEnglish(it.body),
                    costToDollars(it.cost),
                    it.gasolineConsumption
                )
            }
            .sortedWith(compareBy { it.cost })
    }

    private fun brandToEnglish(name: String): String {
        return when (name) {
            "АУДИ" -> "AUDI"
            "БМВ" -> "BMW"
            "ФОРД" -> "FORD"
            "ХОНДА" -> "HONDA"
            "ХЁНДАЙ" -> "HYUNDAI"
            "КИА" -> "KIA"
            "ЛАДА" -> "LADA"
            "МАЗДА" -> "MAZDA"
            "ФОЛКСВАГЕН" -> "VOLKSWAGEN"
            "КАДИЛАК" -> "CADILLAC"
            "ТЕСЛА" -> "TESLA"
            "ПОРШ" -> "PORSCHE"
            else -> "UNKNOWN"
        }
    }

    private fun bodyToEnglish(name: String): String {
        return when (name) {
            "ХЕТЧБЕК" -> "HATCHBACK"
            "СПОРТКАР" -> "SPORTSCAR"
            "ЧЕТЫРЁХДВЕРНОЙСЕДАН" -> "FOURDOORSEDAN"
            "ЛИМУЗИН" -> "LIMOUSINE"
            "КАБРИОЛЕТ" -> "CONVERTIBLE"
            "СЕДАН" -> "HARDTOP"
            "ФУРГОН" -> "VAN"
            "ПИКАП" -> "PICKUPTRUCK"
            else -> "UNKNOWN"
        }
    }

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
}