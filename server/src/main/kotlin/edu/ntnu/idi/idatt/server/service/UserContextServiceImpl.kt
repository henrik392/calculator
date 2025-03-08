package edu.ntnu.idi.idatt.server.service

import org.springframework.stereotype.Service
import org.springframework.web.context.annotation.RequestScope

@Service
@RequestScope
class UserContextServiceImpl : UserContextService {
    private var username: String? = null

    override fun getCurrentUsername(): String {
        return username ?: throw IllegalStateException("No user id set in current context")
    }

    override fun setCurrentUsername(username: String) {
        this.username = username
    }
}