package edu.ntnu.idi.idatt.server.model

import java.time.LocalDateTime

data class HistoryItem(
    val id: Long = 0,
    val expression: String,
    val result: Double,
    val createdAt: LocalDateTime,
    val username: String,
)