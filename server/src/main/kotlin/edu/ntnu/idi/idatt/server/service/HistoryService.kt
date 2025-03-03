package edu.ntnu.idi.idatt.server.service

import edu.ntnu.idi.idatt.server.dto.HistoryItemDTO
import edu.ntnu.idi.idatt.server.dto.PaginatedResponse

interface HistoryService {
    fun getHistory(page: Int, size: Int): PaginatedResponse<HistoryItemDTO>
    fun addHistory(expression: String, result: Double)
}