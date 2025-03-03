package edu.ntnu.idi.idatt.server.repository

import edu.ntnu.idi.idatt.server.model.HistoryItem
import org.springframework.stereotype.Repository
import javax.sql.DataSource

@Repository
class JdbcHistoryRepository(private val dataSource: DataSource) : HistoryRepository {
    override fun getHistory(userId: Long, page: Int, size: Int): Pair<List<HistoryItem>, Int> {
        dataSource.connection.use { conn ->
            val sql = """
                SELECT * FROM calculator_history
                WHERE user_id = ?
                ORDER BY created_at DESC
                LIMIT ?
                OFFSET ?
            """.trimIndent()
            conn.prepareStatement(sql).use { stmt ->
                stmt.setInt(1, userId.toInt())
                stmt.setInt(2, size)
                stmt.setInt(3, (page) * size)

                stmt.executeQuery().use { rows ->
                    val history = mutableListOf<HistoryItem>()
                    while (rows.next()) {
                        history.add(
                            HistoryItem(
                                rows.getLong("id"),
                                rows.getString("expression"),
                                rows.getDouble("result"),
                                rows.getTimestamp("created_at").toLocalDateTime(),
                                rows.getLong("user_id")
                            )
                        )
                    }

                    return Pair(history, history.size)
                }
            }
        }
    }

    override fun addHistory(userId: Long, expression: String, result: Double) {
        dataSource.connection.use { conn ->
            val sql = """
                INSERT INTO calculator_history (expression, result, user_id)
                VALUES (?, ?, ?)
            """.trimIndent()
            conn.prepareStatement(sql).use { stmt ->
                stmt.setString(1, expression)
                stmt.setDouble(2, result)
                stmt.setInt(3, userId.toInt())

                val affectedRows = stmt.executeUpdate()
                if (affectedRows == 0) {
                    throw RuntimeException("Creating history item failed, no rows affected.")
                }
            }
        }
    }
}