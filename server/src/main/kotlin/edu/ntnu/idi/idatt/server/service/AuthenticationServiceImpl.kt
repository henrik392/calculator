package edu.ntnu.idi.idatt.server.service

import edu.ntnu.idi.idatt.server.model.User
import edu.ntnu.idi.idatt.server.repository.UserRepository
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service


@Service
class AuthenticationServiceImpl(
    private val userRepository: UserRepository,
    private val authenticationManager: AuthenticationManager,
    private val passwordEncoder: BCryptPasswordEncoder,
) : AuthenticationService {
    override fun signup(username: String, email: String, password: String): User {
        // Input is already validated with jakarta.validation
        // Check if user already exists
        if (userRepository.existsByUsername(username)) {
            throw IllegalArgumentException("User with username $username already exists")
        }

        val user = User(username, email, passwordEncoder.encode(password))

        return userRepository.save(user)
    }

    override fun authenticate(username: String, password: String): User? {
        if (username.isBlank() || password.isBlank()) {
            throw IllegalArgumentException("Username and password cannot be blank")
        }

        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                username,
                password
            )
        )

        return userRepository.findByUsername(username)
    }
}