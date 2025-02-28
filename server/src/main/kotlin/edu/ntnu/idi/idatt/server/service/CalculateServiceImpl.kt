package edu.ntnu.idi.idatt.server.service

import org.springframework.stereotype.Service

@Service
class CalculateServiceImpl : CalculateService {
    override fun calculate(input: String): Double {
        val cleanInput = input.trim().replace("\\s+".toRegex(), "")
        return 0.1
    }

    private fun evaluateExpression(expression: String): Double {
        return 0.0;
    }
}