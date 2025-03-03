package edu.ntnu.idi.idatt.server.controller

import edu.ntnu.idi.idatt.server.dto.CreateUserRequest
import edu.ntnu.idi.idatt.server.dto.UserResponse
import edu.ntnu.idi.idatt.server.service.UserService
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
            ResponseEntity.ok(UserResponse(user.username, user.email, user.password))
        } catch (e: IllegalArgumentException) {
            logger.error("Calculation error: ${e.message}")
            ResponseEntity.badRequest().body(UserResponse("", "", "", e.message))
        }
    }

    
}