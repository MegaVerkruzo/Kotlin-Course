package ru.tinkoff.fintech.homework.lesson6.configuration

import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.web.client.RestTemplate
import ru.tinkoff.fintech.homework.lesson6.common.model.Cake
import ru.tinkoff.fintech.homework.lesson6.common.model.Order
import java.time.Duration
import javax.sql.DataSource

@Configuration
abstract class ServiceConfiguration {

    @Bean
    fun restTemplate(builder: RestTemplateBuilder): RestTemplate = builder
        .setConnectTimeout(Duration.ofSeconds(CONNECT_TIMEOUT_IN_SECONDS))
        .setReadTimeout(Duration.ofSeconds(READ_TIMEOUT_IN_SECONDS))
        .build()

    @Bean
    fun jdbcTemplate(dataSource: DataSource): JdbcTemplate = JdbcTemplate(dataSource)
}

private const val CONNECT_TIMEOUT_IN_SECONDS = 30L
private const val READ_TIMEOUT_IN_SECONDS = 60L
