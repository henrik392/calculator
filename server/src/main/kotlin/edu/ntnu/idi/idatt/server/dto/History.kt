package edu.ntnu.idi.idatt.server.dto

import jakarta.validation.constraints.Min
import java.time.LocalDateTime

data class PaginatedResponse<T>(
    val content: List<T>,
    val pageNumber: Int,
    val pageSize: Int,
    val totalElements: Int,
    val totalPages: Int,
    val isLast: Boolean,
    val message: String? = null,
    val status: Int = 200
)

data class HistoryItemDTO(
    val expression: String,
    val result: Double,
    val timestamp: LocalDateTime? = LocalDateTime.now()
)

data class HistoryRequest(
    @field:Min(0)
    val page: Int = 0,
    @field:Min(1)
    val size: Int = 10,
)
