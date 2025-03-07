package edu.ntnu.idi.idatt.server.service

import edu.ntnu.idi.idatt.server.model.User

interface AuthenticationService {
    fun signup(username: String, email: String, password: String): User
    fun authenticate(username: String, password: String): User?
}