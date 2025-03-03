package edu.ntnu.idi.idatt.server.controller

import edu.ntnu.idi.idatt.server.dto.CreateUserRequest
import edu.ntnu.idi.idatt.server.dto.LoginRequest
import edu.ntnu.idi.idatt.server.dto.UserResponse
import edu.ntnu.idi.idatt.server.service.UserService
import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/user")
class UserController(private val userService: UserService) {
    private val logger = LoggerFactory.getLogger(UserController::class.java)

    @PostMapping
    fun createUser(@RequestBody request: CreateUserRequest): ResponseEntity<UserResponse> {
        return try {
            val user = userService.createUser(request.username, request.email, request.password)
            ResponseEntity.ok(UserResponse(user.username, user.email))
        } catch (e: IllegalArgumentException) {
            logger.error("Error creating user: ${e.message}")
            ResponseEntity.badRequest().body(UserResponse("", "", e.message))
        }
    }

    @PostMapping("/login")
    fun login(@Valid @RequestBody request: LoginRequest): ResponseEntity<UserResponse> {
        return try {
            val user = userService.login(request.username, request.password)
            if (user == null) {
                logger.info("Could not match username and password for user ${request.username}")
                ResponseEntity.badRequest()
                    .body(UserResponse("", "", "Invalid username or password"))
            } else {
                ResponseEntity.ok(UserResponse(user.username, user.email))
            }
        } catch (e: IllegalArgumentException) {
            logger.error("Error logging in user: ${e.message}")
            ResponseEntity.badRequest().body(UserResponse("", "", e.message))
        }
    }


}