package edu.ntnu.idi.idatt.server.exception

import edu.ntnu.idi.idatt.server.dto.UserResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.time.LocalDateTime

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(ex: MethodArgumentNotValidException): ResponseEntity<ValidationErrorResponse> {
        var errorMessage = "";
        ex.bindingResult.allErrors.forEach { error ->
            if (errorMessage.isNotEmpty()) {
                errorMessage += ", "
            }
            errorMessage += error.defaultMessage
        }

        val errorResponse = ValidationErrorResponse(
            timestamp = LocalDateTime.now(),
            status = 400,
            error = errorMessage
        )

        return ResponseEntity.badRequest().body(errorResponse)
    }
}

data class ValidationErrorResponse (
    val timestamp: LocalDateTime,
    val status: Int,
    val error: String = "Bad Request"
 )
