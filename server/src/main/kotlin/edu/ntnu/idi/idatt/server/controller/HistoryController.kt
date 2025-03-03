package edu.ntnu.idi.idatt.server.controller

import edu.ntnu.idi.idatt.server.dto.HistoryRequest
import edu.ntnu.idi.idatt.server.dto.HistoryItemDTO
import edu.ntnu.idi.idatt.server.dto.PaginatedResponse
import edu.ntnu.idi.idatt.server.service.HistoryService
import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/history")
class HistoryController(private val historyService: HistoryService) {
    private val logger = LoggerFactory.getLogger(HistoryController::class.java)

    // Page
    @GetMapping()
    fun getHistory(@Valid @RequestBody request: HistoryRequest): ResponseEntity<PaginatedResponse<HistoryItemDTO>> {
        return try {
            logger.info("Fetching history for page ${request.page} with size ${request.size}")

            val history = historyService.getHistory(request.page, request.size)
            ResponseEntity.ok(history)
        } catch (e: IllegalArgumentException) {
            logger.error("Error fetching history: ${e.message}")
            ResponseEntity.badRequest()
                .body(PaginatedResponse(listOf(), 0, 0, 0, 0, false, e.message))
        }
    }
}