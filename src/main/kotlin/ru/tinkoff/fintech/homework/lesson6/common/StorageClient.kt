package ru.tinkoff.fintech.homework.lesson6.common

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.*
import ru.tinkoff.fintech.homework.lesson6.common.model.Cake

@Service
class StorageClient(
    private val restTemplate: RestTemplate,
    @Value("\${storage.address}") private val storageAddress: String
) {

    fun getCakes(): Set<Cake> =
        restTemplate.getForObject("$storageAddress$GET_CAKES")

    fun getCake(name: String): Cake? =
        restTemplate.getForObject("$storageAddress$GET_CAKE", name)

    fun updateCake(name: String, cost: Double, count: Int): Cake =
        restTemplate.patchForObject("$storageAddress$PATCH_CAKE", null, name, cost, count)
}

private const val GET_CAKES = "/storage/cake/list"
private const val GET_CAKE = "/storage/cake?name={name}"
private const val PATCH_CAKE = "/storage/cake?name={name}&cost={cost}&count={count}"
