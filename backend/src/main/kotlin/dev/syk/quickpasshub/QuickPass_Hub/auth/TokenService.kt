package dev.syk.quickpasshub.QuickPass_Hub.auth

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.Date
import java.util.UUID
import javax.crypto.SecretKey

@Component
class TokenService {

    // 운영환경에서는 환경변수 및 시크릿 매니저로 한다.
    private val secret : String = "dev-only-super-long-secret-key-please-change-1234567890"

    // lazy: 첫 사용시에만 계산한다.
    private val key: SecretKey by lazy {
        Keys.hmacShaKeyFor(secret.toByteArray(StandardCharsets.UTF_8))
    }

    private val accessMinutes : Long = 15L
    private val refreshDays: Long = 14L

    fun createAccessToken(userName: String, roles: List<String> = listOf("USER")) : String =
        Jwts.builder()
            .subject(userName)
            .claim("roles", roles)
            .issuedAt(Date())
            .expiration(Date.from(Instant.now().plus(accessMinutes, ChronoUnit.MINUTES)))
            .signWith(key, Jwts.SIG.HS256)
            .compact()

    fun createRefreshToken(userName: String): String =
        Jwts.builder()
            .subject(userName)
            .id(UUID.randomUUID().toString()) // jti
            .issuedAt(Date())
            .expiration(Date.from(Instant.now().plus(refreshDays, ChronoUnit.DAYS)))
            .signWith(key, Jwts.SIG.HS256)
            .compact()

    fun parseClaims(token: String) : Claims =
        Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .payload

}