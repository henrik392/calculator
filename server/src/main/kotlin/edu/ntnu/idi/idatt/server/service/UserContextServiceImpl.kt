package edu.ntnu.idi.idatt.server.service

import org.springframework.stereotype.Service
import org.springframework.web.context.annotation.RequestScope

@Service
@RequestScope
class UserContextServiceImpl : UserContextService {
    private var userId: Long? = null

    override fun getCurrentUserId(): Long {
        return userId ?: throw IllegalStateException("No user id set in current context")
    }

    override fun setCurrentUserId(userId: Long) {
        this.userId = userId
    }
}