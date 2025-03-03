package edu.ntnu.idi.idatt.server.dto

data class CreateUserRequest(val username: String, val email: String, val password: String)
data class LoginRequest(val username: String, val password: String)
data class UserResponse(val username: String, val email: String, val password: String, val error: String? = null)
