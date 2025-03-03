package edu.ntnu.idi.idatt.server.controller

import edu.ntnu.idi.idatt.server.dto.CalculationRequest
import edu.ntnu.idi.idatt.server.dto.CalculationResponse
import edu.ntnu.idi.idatt.server.service.CalculateService
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/calculate")
class CalculateController(private val calculateService: CalculateService) {
    private val logger = LoggerFactory.getLogger(CalculateController::class.java)

    @PostMapping
    fun calculate(@RequestBody request: CalculationRequest): ResponseEntity<CalculationResponse> {
        return try {
            logger.info("Calculating the result of the input: ${request.expression}")

            val result = calculateService.calculate(request.expression)

            logger.info("Calculated result: $result")
            ResponseEntity.ok(CalculationResponse( result ))
        } catch (e: IllegalArgumentException) {
            logger.error("Calculation error: ${e.message}")
            ResponseEntity.badRequest().body(CalculationResponse(Double.NaN, e.message))
        }
    }
}