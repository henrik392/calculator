package edu.ntnu.idi.idatt.server.service

import edu.ntnu.idi.idatt.server.model.User
import org.springframework.stereotype.Service

@Service
class UserServiceImpl: UserService {
    override fun createUser(username: String, email: String, password: String): User {
        TODO("Not yet implemented")
    }

    override fun signIn(username: String, password: String): User? {
        TODO("Not yet implemented")
    }
}