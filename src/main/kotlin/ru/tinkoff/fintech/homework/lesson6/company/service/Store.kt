package ru.tinkoff.fintech.homework.lesson6.company.service

import org.springframework.stereotype.Service
import ru.tinkoff.fintech.homework.lesson6.company.model.Cake
import ru.tinkoff.fintech.homework.lesson6.company.model.Feedback
import ru.tinkoff.fintech.homework.lesson6.company.service.client.CakeListClient
import ru.tinkoff.fintech.homework.lesson6.company.service.client.FeedbackListClient

@Service
class Store(val cakeListClient: CakeListClient, val feedbackListClient: FeedbackListClient, val storage: Storage) {
    fun getCakesList(): Set<Cake> {
        return cakeListClient.getCakesList()
    }

    fun getCake(id: Int): Cake {
        return cakeListClient.getCake(id)
    }

    fun consistCake(cake: Cake): Boolean {
        return cakeListClient.consistCake(cake)
    }

    fun addFeedback(feedback: Feedback) {
        feedbackListClient.addFeedback(feedback)
    }
//
//    fun getFeedbackList(cake: Cake): Set<Feedback> =
//        feedbackListClient.getFeed
}