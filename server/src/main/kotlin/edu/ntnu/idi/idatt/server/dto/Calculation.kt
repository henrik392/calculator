package edu.ntnu.idi.idatt.server.dto

import jakarta.validation.constraints.NotBlank

data class CalculationRequest(
    @field:NotBlank
    val expression: String
)

data class CalculationResponse(val result: Double, val error: String? = null)