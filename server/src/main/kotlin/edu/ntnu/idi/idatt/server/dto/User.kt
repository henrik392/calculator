package edu.ntnu.idi.idatt.server.dto

import jakarta.validation.constraints.NotBlank

data class CreateUserRequest(
    @field:NotBlank(message = "Username cannot be empty")
    val username: String,

    @field:NotBlank(message = "Email cannot be empty")
    @field:jakarta.validation.constraints.Email(message = "Invalid email format")
    val email: String,

    @field:NotBlank(message = "Password cannot be empty")
    @field:jakarta.validation.constraints.Size(
        min = 8,
        message = "Password must be at least 8 characters"
    )
    val password: String
)

data class LoginRequest(
    @field:NotBlank(message = "Username cannot be empty")
    val username: String,

    @field:NotBlank(message = "Password cannot be empty")
    val password: String
)

data class UserResponse(
    val username: String,
    val email: String,
    val error: String? = null,
    val success: Boolean = error == null,
    val status: Int = 200,
)