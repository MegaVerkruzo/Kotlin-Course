package ru.tinkoff.fintech.homework.lesson6.storage

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import ru.tinkoff.fintech.homework.lesson6.common.model.Cake

@Repository
class StorageRepository(private val jdbcTemplate: JdbcTemplate) : StorageDao {
    override fun getCakes(): Set<Cake> = jdbcTemplate.query(
        "SELECT * FROM cakes"
    ) { rs, _ ->
        Cake(
            rs.getString("cake_name"),
            rs.getDouble("cake_cost"),
            rs.getInt("cake_count")
        )
    }.toSet()

    override fun getCake(name: String): Cake? = jdbcTemplate.query(
        "SELECT * FROM cakes WHERE cake_name == \"$name\""
    ) { rs, _ ->
        Cake(
            rs.getString("cake_name"),
            rs.getDouble("cake_cost"),
            rs.getInt("cake_count")
        )
    }[0]

    override fun updateCake(cake: Cake): Cake = jdbcTemplate.query(
        "UPDATE cakes SET cake_cost = ${cake.cost}, cake_count = ${cake.count} WHERE cake_name == ${cake.name}"
    ) { rs, _ ->
        cake
    }[0]

    fun addCake(cake: Cake): Cake = jdbcTemplate.query(
        "INSERT INTO cakes (cake_name, cake_cost, cake_count) VALUES (${cake.name}, ${cake.cost}, ${cake.count})"
    ) { rs, _ ->
        cake
    }[0]
}