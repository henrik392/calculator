package edu.ntnu.idi.idatt.server.dto

data class CalculationRequest(val expression: String)
data class CalculationResponse(val result: Double, val error: String? = null)