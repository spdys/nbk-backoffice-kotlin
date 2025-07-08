package com.nbk.test.service

import com.nbk.test.entity.UserEntity
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.time.Instant
import java.util.Date
import javax.crypto.SecretKey

@Component
class JwtService(@Value("\${jwt.secret}") secret: String) {
    private val secretKey: SecretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret))
    private val expirationMs: Long = 1000 * 60 * 60

    fun generateToken(user: UserEntity): String {
        val now = Instant.now()
        val expiry = now.plusMillis(expirationMs)

        return Jwts.builder()
            .subject(user.username)
            .claim("role", user.role.name)
            .claim("userId", user.id)
            .issuedAt(Date.from(now))
            .expiration(Date.from(expiry))
            .signWith(secretKey)
            .compact()
    }

    fun extractUsername(token: String): String =
        parseToken(token).subject

    fun extractRole(token: String): String =
        parseToken(token).get("role", String::class.java)

    fun isTokenValid(token: String, username: String): Boolean =
        extractUsername(token) == username

    private fun parseToken(token: String): Claims =
        Jwts.parser()
            .verifyWith(secretKey)
            .build()
            .parseSignedClaims(token)
            .payload
}