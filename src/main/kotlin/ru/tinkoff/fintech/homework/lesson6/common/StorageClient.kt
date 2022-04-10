package ru.tinkoff.fintech.homework.lesson6.common

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.*
import ru.tinkoff.fintech.homework.lesson6.model.Cake

@Service
class StorageClient(
    private val restTemplate: RestTemplate,
    @Value("\${storage.address}") private val storageAddress: String
) {

    fun getCakesSet(): Set<Cake> =
        restTemplate.exchange<Set<Cake>>("$storageAddress$GET_CAKE_SET", HttpMethod.GET).body!!

    fun consistCakeType(name: String): Boolean =
        restTemplate.getForObject("$storageAddress$GET_CONSIST_CAKE", HttpMethod.GET, name)

    fun getCake(name: String): Cake =
        restTemplate.getForObject("$storageAddress$GET_CAKE", HttpMethod.GET, name)

    fun updateCakesParams(name: String, cost: Double, count: Int) {
        restTemplate.patchForObject<Unit>("$storageAddress$PATCH_CAKE", HttpMethod.PATCH, name, cost, count)
    }
}

private const val GET_CAKE_SET = "/storage/cake/set"
private const val GET_CONSIST_CAKE = "/storage/cake/consist?name={name}"
private const val GET_CAKE = "/storage/cake?name={name}"
private const val PATCH_CAKE = "?name={name}&cost={cost}&count={count}"