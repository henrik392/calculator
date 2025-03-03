package edu.ntnu.idi.idatt.server.service

import edu.ntnu.idi.idatt.server.model.User
import edu.ntnu.idi.idatt.server.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(private val userRepository: UserRepository) : UserService {
    override fun createUser(username: String, email: String, password: String): User {
        // Input is already validated with jakarta.validation
        // Check if user already exists
        if (userRepository.existsByUsername(username)) {
            throw IllegalArgumentException("User with username $username already exists")
        }

        return userRepository.save(username, email, password)
    }

    override fun login(username: String, password: String): User? {
        if (username.isBlank() || password.isBlank()) {
            throw IllegalArgumentException("Username and password cannot be blank")
        }

        return userRepository.login(username, password)
    }
}