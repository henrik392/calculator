package edu.ntnu.idi.idatt.server.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import java.util.Date

class JwtTokenUtil {
    @Value("\${jwt.secret:da75e38d326dfa8d3f3bda77a94dc7ad1f759084e8c1573a9d5077b30b1c6ced}")
    private lateinit var secret: String

    @Value("\${jwt.expiration:300000}") // 5 minutes
    private var expiration: Long = 0

    fun generateToken(userId: Long): String {
        val now = System.currentTimeMillis();
        val expirationTime = now + expiration;

        return io.jsonwebtoken.Jwts.builder()
            .subject(userId.toString())
            .issuedAt(Date(now))
            .expiration(Date(expirationTime))
            .signWith(Keys.hmacShaKeyFor(secret.toByteArray()))
            .compact()
    }

    fun getUserIdFromToken(token: String): Long {
        val claims = getAllClaimsFromToken(token)
        return claims.subject.toLong()
    }

    fun validateToken(token: String, userId: Long): Boolean {
        return userId == getUserIdFromToken(token) && !isTokenExpired(token)
    }

    private fun isTokenExpired(token: String): Boolean {
        val expirationDate = getAllClaimsFromToken(token).expiration
        val now = Date()

        return now.after(expirationDate)
    }

    private fun getAllClaimsFromToken(token: String): Claims {
        return Jwts.parser()
            .verifyWith(Keys.hmacShaKeyFor(secret.toByteArray()))
            .build()
            .parseSignedClaims(token)
            .payload
    }
}