package edu.ntnu.idi.idatt.server.service

import edu.ntnu.idi.idatt.server.dto.HistoryItemDTO
import edu.ntnu.idi.idatt.server.dto.PaginatedResponse
import edu.ntnu.idi.idatt.server.model.HistoryItem
import edu.ntnu.idi.idatt.server.repository.HistoryRepository
import org.springframework.stereotype.Service

@Service
class HistoryServiceImpl(
    private val historyRepository: HistoryRepository,
    private val userContextService: UserContextService
) : HistoryService {
    override fun getHistory(page: Int, size: Int): PaginatedResponse<HistoryItemDTO> {
        val userId = userContextService.getCurrentUserId()
        val (items, totalCount) = historyRepository.getHistory(userId, page, size)

        val dtos = items.map { it.toDTO() }
        val totalPages = (totalCount + size - 1) / size
        val isLast = page >= totalPages - 1

        return PaginatedResponse(
            content = dtos,
            pageNumber = page,
            pageSize = size,
            totalElements = totalCount,
            totalPages = totalPages,
            isLast = isLast
        )
    }

    override fun addHistory(expression: String, result: Double) {
        val userId = userContextService.getCurrentUserId()
        historyRepository.addHistory(userId, expression, result)
    }

    private fun HistoryItem.toDTO() = HistoryItemDTO(
        expression = expression,
        result = result,
        timestamp = createdAt
    )
}