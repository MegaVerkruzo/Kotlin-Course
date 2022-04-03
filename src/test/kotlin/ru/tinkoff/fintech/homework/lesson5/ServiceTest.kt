package ru.tinkoff.fintech.homework.lesson5

import io.kotest.core.spec.style.FeatureSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldContainAll

class ServiceTest : FeatureSpec() {
    val carTranslatingService = CarTranslatingService(
        brandOnEnglish,
        bodyOnEnglish,
        modelOnEnglish
    )
    val carProcessingService = CarProcessingService(
        carTranslatingService
    )

    init {
        feature("Проверка сервиса") {
            scenario("Проверка перевода на Американский рынок") {
                val result = listOf(
                    audiTTSOnEng,
                    ladaVestaOnEng,
                    bmwM4OnEng,
                    porshe911OnEng,
                    bmwXDriveOnEng,
                    bmwXSpecialOnEng
                )

                carProcessingService.translateToEnglishSorted(carsOnRus) shouldContainAll result
            }

            scenario("Проверка группировки по брэнду") {
                val bmwCars = listOf(
                    bmwXDriveOnRus,
                    bmwXSpecialOnRus,
                    bmwM4OnRus
                )

                val groupedCars = carProcessingService.groupByBrand(carsOnRus)

                groupedCars["БМВ"]?.shouldContainAll(bmwCars)
                groupedCars["ПОРШ"]?.shouldContain(porshe911OnRus)
                groupedCars["ЛАДА"]?.shouldContain(ladaVestaOnRus)
                groupedCars["АУДИ"]?.shouldContain(audiTTSOnRus)
            }

            scenario("Проверка группировки по кузову") {
                val sportsCar = listOf(
                    audiTTSOnRus,
                    porshe911OnRus,
                    bmwM4OnRus
                )

                val groupedCars = carProcessingService.groupByBody(carsOnRus)

                groupedCars["СПОРТКАР"]?.shouldContainAll(sportsCar)
                groupedCars["ЧЕТЫРЁХДВЕРНОЙСЕДАН"]?.shouldContain(ladaVestaOnRus)
                groupedCars["ЛИМУЗИН"]?.shouldContain(bmwXDriveOnRus)
                groupedCars["СЕДАН"]?.shouldContain(bmwXSpecialOnRus)


            }

            scenario("Топ 3 дешёвых машины PORSCHE и BMW") {
                val result = listOf(
                    porshe911OnEng,
                    bmwXDriveOnEng,
                    bmwM4OnEng,
                    bmwXSpecialOnEng
                )

                result shouldContainAll carProcessingService.suitableList(carsOnRus) { car: Car -> car.brand == "БМВ" || car.brand == "ПОРШ" }
            }
        }
    }
}

val carsOnRus = listOf(
    Car("730лд иксДрайв", "БМВ", "ЛИМУЗИН", 10114500.0, 3.0),
    Car("икс6 М50д М спешиал", "БМВ", "СЕДАН", 17999000.0, 3.0),
    Car("911 каррера", "ПОРШ", "СПОРТКАР", 8790000.0, 9.6),
    Car("Веста", "ЛАДА", "ЧЕТЫРЁХДВЕРНОЙСЕДАН", 1038000.0, 6.3),
    Car("М4", "БМВ", "СПОРТКАР", 7800000.0, 8.3),
    Car("ТТС", "АУДИ", "СПОРТКАР", 2740000.0, 6.4)
)

val bmwXDriveOnRus = Car("730лд иксДрайв", "БМВ", "ЛИМУЗИН", 10114500.0, 3.0)
val bmwXDriveOnEng = Car(
    model = "730Ld xDrive",
    brand = "BMW",
    body = "LIMOUSINE",
    cost = 101145.0,
    gasolineConsumption = 3.0
)

val bmwXSpecialOnRus = Car("икс6 М50д М спешиал", "БМВ", "СЕДАН", 17999000.0, 3.0)
val bmwXSpecialOnEng = Car(
    model = "X6 M50d M Special",
    brand = "BMW",
    body = "HARDTOP",
    cost = 179990.0,
    gasolineConsumption = 3.0
)

val porshe911OnRus = Car("911 каррера", "ПОРШ", "СПОРТКАР", 8790000.0, 9.6)
val porshe911OnEng = Car(
    model = "911 Carrera",
    brand = "PORSCHE",
    body = "SPORTSCAR",
    cost = 87900.0,
    gasolineConsumption = 9.6
)

val ladaVestaOnRus = Car("Веста", "ЛАДА", "ЧЕТЫРЁХДВЕРНОЙСЕДАН", 1038000.0, 6.3)
val ladaVestaOnEng = Car(
    model = "TTS",
    brand = "AUDI",
    body = "SPORTSCAR",
    cost = 27400.0,
    gasolineConsumption = 6.4
)

val bmwM4OnRus = Car("М4", "БМВ", "СПОРТКАР", 7800000.0, 8.3)
val bmwM4OnEng = Car(
    model = "M4",
    brand = "BMW",
    body = "SPORTSCAR",
    cost = 78000.0,
    gasolineConsumption = 8.3
)

val audiTTSOnRus = Car("ТТС", "АУДИ", "СПОРТКАР", 2740000.0, 6.4)
val audiTTSOnEng = Car(
    model = "Vesta",
    brand = "LADA",
    body = "FOURDOORSEDAN",
    cost = 10380.0,
    gasolineConsumption = 6.3
)

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
