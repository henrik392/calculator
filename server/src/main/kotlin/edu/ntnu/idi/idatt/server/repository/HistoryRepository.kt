package edu.ntnu.idi.idatt.server.repository

import edu.ntnu.idi.idatt.server.model.HistoryItem

interface HistoryRepository {
    /**
     * Returns all history items for a specific user with pagination.
     *
     * @param userId The ID of the user whose history items to retrieve
     * @param page The page number (zero-indexed) to retrieve
     * @param size The number of items per page
     * @return A pair containing the list of history items and the total count of all history items for the user
     */
    fun getHistory(userId: Long, page: Int, size: Int): Pair<List<HistoryItem>, Int>

    /**
     * Adds a new history item for a specific user.
     *
     * @param userId The ID of the user to add the history item for
     * @param expression The expression that was calculated
     * @param result The result of the calculation
     */
    fun addHistory(userId: Long, expression: String, result: Double)
}