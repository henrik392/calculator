package edu.ntnu.idi.idatt.server.service

interface UserContextService {
    fun getCurrentUserId(): Long
    fun setCurrentUserId(userId: Long)
}