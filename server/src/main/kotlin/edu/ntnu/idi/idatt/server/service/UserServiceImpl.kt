package edu.ntnu.idi.idatt.server.service

import edu.ntnu.idi.idatt.server.model.User
import org.springframework.stereotype.Service
import java.sql.Statement
import javax.sql.DataSource

@Service
class UserServiceImpl(private val dataSource: DataSource) : UserService {
    override fun createUser(username: String, email: String, password: String): User {
        // Validate input
        if (username.isBlank() || email.isBlank() || password.isBlank()) {
            throw IllegalArgumentException("Username, email and password cannot be blank")
        }

        if (userExists(username)) {
            throw IllegalArgumentException("User with username $username already exists")
        }

        dataSource.connection.use { conn ->
            val sql = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)"
            conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS).use { stmt ->
                stmt.setString(1, username)
                stmt.setString(2, email)
                // To be replaced with JWT token
                stmt.setString(3, password)

                val affectedRows = stmt.executeUpdate()
                if (affectedRows == 0) {
                    throw RuntimeException("Creating user failed, no rows affected.")
                }

                stmt.generatedKeys.use { keys ->
                    if (keys.next()) {
                        val id = keys.getLong(1)
                        return User(id, username, email, password)
                    } else {
                        throw RuntimeException("Creating user failed, no ID obtained.")
                    }
                }
            }
        }
    }

    override fun signIn(username: String, password: String): User? {
        TODO("Not yet implemented")
    }

    private fun userExists(username: String): Boolean {
        dataSource.connection.use { conn ->
            val sql = "SELECT COUNT(*) FROM users WHERE username = ?"
            conn.prepareStatement(sql).use { stmt ->
                stmt.setString(1, username)
                stmt.executeQuery().use { rows ->
                    if (rows.next()) {
                        return rows.getInt(1) > 0
                    }
                }
            }
        }

        return false
    }
}