package edu.ntnu.idi.idatt.server.service

import edu.ntnu.idi.idatt.server.model.User
import edu.ntnu.idi.idatt.server.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserServiceImpl(private val userRepository: UserRepository) : UserService {
    override fun createUser(username: String, email: String, password: String): User {
        // Validate input
        if (username.isBlank() || email.isBlank() || password.isBlank()) {
            throw IllegalArgumentException("Username, email and password cannot be blank")
        }

        if (userRepository.existsByUsername(username)) {
            throw IllegalArgumentException("User with username $username already exists")
        }

        return userRepository.save(username, email, password)
    }

    override fun signIn(username: String, password: String): User? {
        TODO("Not yet implemented")
    }
}