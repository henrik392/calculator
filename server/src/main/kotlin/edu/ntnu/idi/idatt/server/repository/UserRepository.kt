package edu.ntnu.idi.idatt.server.repository

import edu.ntnu.idi.idatt.server.model.User

interface UserRepository {
    fun save(username: String, email: String, password: String): User
    fun existsByUsername(username: String): Boolean
}