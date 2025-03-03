package edu.ntnu.idi.idatt.server.service

import org.springframework.stereotype.Service

@Service
class CalculateServiceImpl(
    private val historyService: HistoryService,
) : CalculateService {
    override fun calculate(input: String): Double {
        val cleanInput = input.replace("\\s+".toRegex(), "").replace("\"", "")
        val result = evaluateExpression(cleanInput)

        // Add to history
        historyService.addHistory(cleanInput, result)

        return result;
    }

    private fun evaluateExpression(expression: String): Double {
        var processedExpression = expression;

        val mulDivRegex = "(-?\\d+\\.?\\d*)([*/])(-?\\d+\\.?\\d*)".toRegex()
        while (mulDivRegex.containsMatchIn(processedExpression)) {
            processedExpression = mulDivRegex.replace(processedExpression) { matchResult ->
                val (a, op, b) = matchResult.destructured
                val result = when (op) {
                    "*" -> a.toDouble() * b.toDouble()
                    "/" -> a.toDouble() / b.toDouble()
                    else -> 0.0
                }
                result.toString()
            }
        }

        val addSubRegex = "(-?\\d+\\.?\\d*)([+\\-])(\\d+\\.?\\d*)".toRegex()
        while (addSubRegex.containsMatchIn(processedExpression)) {
            processedExpression = addSubRegex.replace(processedExpression) { matchResult ->
                val (a, op, b) = matchResult.destructured
                val result = when (op) {
                    "+" -> a.toDouble() + b.toDouble()
                    "-" -> a.toDouble() - b.toDouble()
                    else -> 0.0
                }
                result.toString()
            }
        }

        return try {
            processedExpression.toDouble()
        } catch (e: NumberFormatException) {
            throw IllegalArgumentException("Invalid expression: $expression")
        }
    }
}