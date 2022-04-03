package ru.tinkoff.fintech.homework.lesson6.company.service

import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import ru.tinkoff.fintech.homework.lesson6.company.model.Cake
import ru.tinkoff.fintech.homework.lesson6.company.model.Feedback
import ru.tinkoff.fintech.homework.lesson6.company.service.client.CakeListClient
import ru.tinkoff.fintech.homework.lesson6.company.service.client.FeedbackListClient

@Service
class Confectionery(val cakeListClient: CakeListClient, val feedbackListClient: FeedbackListClient, val storage: Storage) {
    fun getCakesList(): Set<Cake> {
        return cakeListClient.getCakesList()
    }

    fun consistCake(): Boolean {
        return cakeListClient.consistCake()
    }

    fun addFeedback(feedback: Feedback) {
        feedbackListClient.addFeedback(feedback)
    }

    fun getFeedbackList(): Set<Feedback> =
        feedbackListClient.getFeed
}