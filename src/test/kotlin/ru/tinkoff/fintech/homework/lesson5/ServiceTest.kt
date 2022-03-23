package ru.tinkoff.fintech.homework.lesson5

import io.kotest.core.spec.style.FeatureSpec
import io.kotest.matchers.shouldBe

class ServiceTest : FeatureSpec() {
    val service = Service()

    init {
        feature("Проверка сервиса") {
            scenario("Проверка перевода на Американский рынок") {
                val translatedDescription = service.onEnglish(cars)

                print(translatedDescription)
                translatedDescription[0] shouldBe Car(model="Vesta", brand="LADA", body="FOURDOORSEDAN", cost=10380.0, gasolineConsumption=6.3)
                translatedDescription[1] shouldBe Car(model="TTS", brand="AUDI", body="SPORTSCAR", cost=27400.0, gasolineConsumption=6.4)
                translatedDescription[2] shouldBe Car(model="M4", brand="BMW", body="SPORTSCAR", cost=78000.0, gasolineConsumption=8.3)
                translatedDescription[3] shouldBe Car(model="911 Carrera", brand="PORSCHE", body="SPORTSCAR", cost=87900.0, gasolineConsumption=9.6)
                translatedDescription[4] shouldBe Car(model="730Ld xDrive", brand="BMW", body="LIMOUSINE", cost=101145.0, gasolineConsumption=3.0)
                translatedDescription[5] shouldBe Car(model="X6 M50d M Special", brand="BMW", body="HARDTOP", cost=179990.0, gasolineConsumption=3.0)
            }

            scenario("Проверка группировки по брэнду") {
                val groupedCars = service.groupByBrand(cars)

                println(groupedCars)
            }

            scenario("Проверка группировки по кузову") {
                val groupedCars = service.groupByBrand(cars)

                println(groupedCars)
            }

            scenario("Топ 3 дешёвых машины PORSCHE и BMW") {
                val groupOfCars = service.suitableList(cars) { car: Car -> car.brand == "БМВ" || car.brand == "ПОРШ" }

                println(groupOfCars)
                groupOfCars.size shouldBe 3
                groupOfCars[0] shouldBe Car(model="M4", brand="BMW", body="SPORTSCAR", cost=78000.0, gasolineConsumption=8.3)
                groupOfCars[1] shouldBe Car(model="911 Carrera", brand="PORSCHE", body="SPORTSCAR", cost=87900.0, gasolineConsumption=9.6)
                groupOfCars[2] shouldBe Car(model="730Ld xDrive", brand="BMW", body="LIMOUSINE", cost=101145.0, gasolineConsumption=3.0)
            }
        }
    }
}

val cars = listOf(
    Car("730лд иксДрайв", "БМВ", "ЛИМУЗИН", 10114500.0, 3.0),
    Car("икс6 М50д М спешиал", "БМВ", "СЕДАН", 17999000.0, 3.0),
    Car("911 каррера", "ПОРШ", "СПОРТКАР", 8790000.0,9.6),
    Car("Веста", "ЛАДА", "ЧЕТЫРЁХДВЕРНОЙСЕДАН", 1038000.0, 6.3),
    Car("М4", "БМВ", "СПОРТКАР", 7800000.0, 8.3),
    Car("ТТС", "АУДИ", "СПОРТКАР", 2740000.0, 6.4)
)