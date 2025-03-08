package edu.ntnu.idi.idatt.server.service

interface UserContextService {
    fun getCurrentUsername(): String
    fun setCurrentUsername(username: String)
}