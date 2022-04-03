package ru.tinkoff.fintech.homework.lesson6.company.service.client

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import ru.tinkoff.fintech.homework.lesson6.company.model.Feedback

@Service
class FeedbackListClient(
    val restTemplate: RestTemplate,
    @Value("\${feedback.list.address}") private val feedbackListAddress: String
) {
    fun addFeedback(feedback: Feedback) {

    }

//    fun getFeedbackList(): Set<Feedback> =
//        restTemplate.exchange<Set<Feedback>>("$feedbackListAddress$")
}

