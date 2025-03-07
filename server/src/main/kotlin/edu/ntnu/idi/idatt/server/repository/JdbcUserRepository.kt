package edu.ntnu.idi.idatt.server.repository

import edu.ntnu.idi.idatt.server.model.User
import org.springframework.stereotype.Repository
import java.sql.Statement
import javax.sql.DataSource

@Repository
class JdbcUserRepository(private val dataSource: DataSource) : UserRepository {

    override fun save(username: String, email: String, password: String): User {
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

    override fun existsByUsername(username: String): Boolean {
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

    override fun findByUsername(username: String): User? {
        dataSource.connection.use { conn ->
            val sql = "SELECT * FROM users WHERE username = ?"
            conn.prepareStatement(sql).use { stmt ->
                stmt.setString(1, username)
                stmt.executeQuery().use { rows ->
                    if (rows.next()) {
                        return User(
                            rows.getLong("id"),
                            rows.getString("username"),
                            rows.getString("email"),
                            rows.getString("password")
                        )
                    }
                }
            }
        }

        return null
    }

    override fun findByUserId(userId: Long): User? {
        dataSource.connection.use { conn ->
            val sql = "SELECT * FROM users WHERE id = ?"
            conn.prepareStatement(sql).use { stmt ->
                stmt.setLong(1, userId)
                stmt.executeQuery().use { rows ->
                    if (rows.next()) {
                        return User(
                            rows.getLong("id"),
                            rows.getString("username"),
                            rows.getString("email"),
                            rows.getString("password")
                        )
                    }
                }
            }
        }

        return null
    }

    override fun login(username: String, password: String): User? {
        dataSource.connection.use { conn ->
            val sql = "SELECT * FROM users WHERE username = ? AND password = ?"
            conn.prepareStatement(sql).use { stmt ->
                stmt.setString(1, username)
                stmt.setString(2, password)
                stmt.executeQuery().use { rows ->
                    if (rows.next()) {
                        return User(
                            rows.getLong("id"),
                            rows.getString("username"),
                            rows.getString("email"),
                            rows.getString("password")
                        )
                    }
                }
            }
        }

        return null
    }
}