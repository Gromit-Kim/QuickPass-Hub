package dev.syk.quickpasshub.QuickPass_Hub.auth

import org.springframework.http.HttpCookie
import org.springframework.http.ResponseCookie
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class CookieSupport {

    fun writeRefreshTokenCookie(
        token: String,
        secure: Boolean = false, // HTTPS라면 secure=true
        sameSite: String = "Lax",
        path: String = "/",
        maxAge: Duration = Duration.ofDays(14)
    ) : ResponseCookie =
        ResponseCookie
            .from("refresh", token)
            .httpOnly(true)
            .secure(secure)
            .sameSite(sameSite)
            .path(path)
            .maxAge(maxAge)
            .build()

    fun clearRefreshTokenCookie(
        secure: Boolean = false,
        sameSite : String = "Lax",
        path: String = "/",
    ) : ResponseCookie =
        ResponseCookie
            .from("refresh", "")
            .httpOnly(true)
            .secure(secure)
            .sameSite(sameSite)
            .path(path)
            .maxAge(0) // 삭제
            .build()
}