package edu.ntnu.idi.idatt.server.controller

import edu.ntnu.idi.idatt.server.dto.HistoryItemDTO
import edu.ntnu.idi.idatt.server.dto.PaginatedResponse
import edu.ntnu.idi.idatt.server.service.HistoryService
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/history")
class HistoryController(private val historyService: HistoryService) {
    private val logger = LoggerFactory.getLogger(HistoryController::class.java)

    // Page
    @GetMapping()
    fun getHistory(
        @RequestParam(defaultValue = "0") @Min(value = 0, message = "Page index must be non-negative")
        page: Int,
        @RequestParam(defaultValue = "10") @Min(value = 1, message = "Page size must be positive")
        @Max(value = 100, message = "Page size must not exceed 100")
        size: Int
    ): ResponseEntity<PaginatedResponse<HistoryItemDTO>> {
        return try {
            logger.info("Fetching history for page $page with size $size")

            val history = historyService.getHistory(page, size)
            ResponseEntity.ok(history)
        } catch (e: IllegalArgumentException) {
            logger.error("Error fetching history: ${e.message}")
            ResponseEntity.badRequest()
                .body(PaginatedResponse(listOf(), 0, 0, 0, 0, false, e.message))
        }
    }
}