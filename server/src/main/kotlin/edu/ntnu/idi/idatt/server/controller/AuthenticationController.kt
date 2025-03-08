package edu.ntnu.idi.idatt.server.controller

import edu.ntnu.idi.idatt.server.dto.CreateUserRequest
import edu.ntnu.idi.idatt.server.dto.LoginRequest
import edu.ntnu.idi.idatt.server.dto.UserResponse
import edu.ntnu.idi.idatt.server.security.JwtService
import edu.ntnu.idi.idatt.server.service.AuthenticationService
import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthenticationController(
    private val authenticationService: AuthenticationService,
    private val jwtService: JwtService
) {
    private val logger = LoggerFactory.getLogger(AuthenticationController::class.java)

    @PostMapping("/signup")
    fun signup(@RequestBody request: CreateUserRequest): ResponseEntity<UserResponse> {
        return try {
            val user = authenticationService.signup(request.username, request.email, request.password)
            val jwtToken = jwtService.generateToken(request.username)
            ResponseEntity.ok(UserResponse(user.username, user.email, jwtToken, jwtService.getExpirationTime()))
        } catch (e: IllegalArgumentException) {
            logger.error("Error creating user: ${e.message}")
            ResponseEntity.badRequest().body(UserResponse("", "", "", 0, e.message))
        }
    }

    @PostMapping("/login")
    fun login(@Valid @RequestBody request: LoginRequest): ResponseEntity<UserResponse> {
        return try {
            val user = authenticationService.authenticate(request.username, request.password)
            if (user == null) {
                logger.info("Could not match username and password for user ${request.username}")
                ResponseEntity.badRequest()
                    .body(UserResponse("", "", "", 0, "Invalid username or password", 400))
            } else {
                val jwtToken = jwtService.generateToken(request.username)

                logger.info("Generated token for user ${request.username}")
                ResponseEntity.ok(UserResponse(user.username, user.email, jwtToken, jwtService.getExpirationTime()))
            }
        } catch (e: IllegalArgumentException) {
            logger.error("Error logging in user: ${e.message}")
            ResponseEntity.badRequest().body(UserResponse("", "", "", 0, e.message, 400))
        }
    }


}