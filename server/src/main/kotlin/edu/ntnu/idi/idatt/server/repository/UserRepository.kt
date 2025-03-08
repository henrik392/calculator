package edu.ntnu.idi.idatt.server.repository

import edu.ntnu.idi.idatt.server.model.User

interface UserRepository {
    fun save(user: User): User
    fun existsByUsername(username: String): Boolean
    fun findByUsername(username: String): User?
}