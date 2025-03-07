package edu.ntnu.idi.idatt.server.service

import edu.ntnu.idi.idatt.server.model.User

interface UserService {
    fun createUser(username: String, email: String, password: String): User
    fun login(username: String, password: String): User?
}