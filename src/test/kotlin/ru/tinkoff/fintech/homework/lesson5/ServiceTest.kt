package ru.tinkoff.fintech.homework.lesson5

import io.kotest.core.spec.style.FeatureSpec
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldContainAll
import io.kotest.matchers.maps.shouldContainAll
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe

class ServiceTest : FeatureSpec() {
    val service = Service()

    init {
        feature("Проверка сервиса") {
            scenario("Проверка перевода на Американский рынок") {
                val result = listOf(
                    Car(
                        model = "TTS",
                        brand = "AUDI",
                        body = "SPORTSCAR",
                        cost = 27400.0,
                        gasolineConsumption = 6.4
                    ),
                    Car(
                        model = "Vesta",
                        brand = "LADA",
                        body = "FOURDOORSEDAN",
                        cost = 10380.0,
                        gasolineConsumption = 6.3
                    ),
                    Car(
                        model = "M4",
                        brand = "BMW",
                        body = "SPORTSCAR",
                        cost = 78000.0,
                        gasolineConsumption = 8.3
                    ),
                    Car(
                        model = "911 Carrera",
                        brand = "PORSCHE",
                        body = "SPORTSCAR",
                        cost = 87900.0,
                        gasolineConsumption = 9.6
                    ),
                    Car(
                        model = "730Ld xDrive",
                        brand = "BMW",
                        body = "LIMOUSINE",
                        cost = 101145.0,
                        gasolineConsumption = 3.0
                    ),
                    Car(
                        model = "X6 M50d M Special",
                        brand = "BMW",
                        body = "HARDTOP",
                        cost = 179990.0,
                        gasolineConsumption = 3.0
                    )
                )

                service.onEnglish(cars) shouldContainAll result
            }

            scenario("Проверка группировки по брэнду") {
                val bmwCars = listOf(
                    Car(
                        "730лд иксДрайв",
                        "БМВ",
                        "ЛИМУЗИН",
                        10114500.0,
                        3.0
                    ),
                    Car(
                        "икс6 М50д М спешиал",
                        "БМВ",
                        "СЕДАН",
                        17999000.0,
                        3.0
                    ),
                    Car(
                        "М4",
                        "БМВ",
                        "СПОРТКАР",
                        7800000.0,
                        8.3
                    )
                )

                val groupedCars = service.groupByBrand(cars)

                groupedCars["БМВ"]?.shouldContainAll(bmwCars)
                groupedCars["ПОРШ"]?.shouldContain(
                    Car(
                        "911 каррера",
                        "ПОРШ",
                        "СПОРТКАР",
                        8790000.0,
                        9.6
                    )
                )
                groupedCars["ЛАДА"]?.shouldContain(
                    Car(
                        "Веста",
                        "ЛАДА",
                        "ЧЕТЫРЁХДВЕРНОЙСЕДАН",
                        1038000.0,
                        6.3
                    )
                )
                groupedCars["АУДИ"]?.shouldContain(
                    Car(
                        "ТТС",
                        "АУДИ",
                        "СПОРТКАР",
                        2740000.0,
                        6.4
                    )
                )
            }

            scenario("Проверка группировки по кузову") {
                val sportsCar = listOf(
                    Car(
                        "ТТС",
                        "АУДИ",
                        "СПОРТКАР",
                        2740000.0,
                        6.4
                    ),
                    Car(
                        "911 каррера",
                        "ПОРШ",
                        "СПОРТКАР",
                        8790000.0,
                        9.6
                    ),
                    Car(
                        "М4",
                        "БМВ",
                        "СПОРТКАР",
                        7800000.0,
                        8.3
                    )
                )

                val groupedCars = service.groupByBody(cars)

                groupedCars["СПОРТКАР"]?.shouldContainAll(sportsCar)
                groupedCars["ЧЕТЫРЁХДВЕРНОЙСЕДАН"]?.shouldContain(
                    Car(
                        "Веста",
                        "ЛАДА",
                        "ЧЕТЫРЁХДВЕРНОЙСЕДАН",
                        1038000.0,
                        6.3
                    )
                )
                groupedCars["ЛИМУЗИН"]?.shouldContain(
                    Car(
                        "730лд иксДрайв",
                        "БМВ",
                        "ЛИМУЗИН",
                        10114500.0,
                        3.0
                    )
                )
                groupedCars["СЕДАН"]?.shouldContain(
                    Car(
                        "икс6 М50д М спешиал",
                        "БМВ",
                        "СЕДАН",
                        17999000.0,
                        3.0
                    )
                )


            }

            scenario("Топ 3 дешёвых машины PORSCHE и BMW") {
                val result = listOf(
                    Car(
                        model = "911 Carrera",
                        brand = "PORSCHE",
                        body = "SPORTSCAR",
                        cost = 87900.0,
                        gasolineConsumption = 9.6
                    ),
                    Car(
                        model = "730Ld xDrive",
                        brand = "BMW",
                        body = "LIMOUSINE",
                        cost = 101145.0,
                        gasolineConsumption = 3.0
                    ),
                    Car(
                        model = "M4",
                        brand = "BMW",
                        body = "SPORTSCAR",
                        cost = 78000.0,
                        gasolineConsumption = 8.3
                    )
                )

                service.suitableList(cars) { car: Car -> car.brand == "БМВ" || car.brand == "ПОРШ" } shouldContainAll result
            }
        }
    }
}

val cars = listOf(
    Car("730лд иксДрайв", "БМВ", "ЛИМУЗИН", 10114500.0, 3.0),
    Car("икс6 М50д М спешиал", "БМВ", "СЕДАН", 17999000.0, 3.0),
    Car("911 каррера", "ПОРШ", "СПОРТКАР", 8790000.0, 9.6),
    Car("Веста", "ЛАДА", "ЧЕТЫРЁХДВЕРНОЙСЕДАН", 1038000.0, 6.3),
    Car("М4", "БМВ", "СПОРТКАР", 7800000.0, 8.3),
    Car("ТТС", "АУДИ", "СПОРТКАР", 2740000.0, 6.4)
)
