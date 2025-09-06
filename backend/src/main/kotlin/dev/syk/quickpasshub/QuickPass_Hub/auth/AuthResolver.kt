package dev.syk.quickpasshub.QuickPass_Hub.auth

import dev.syk.quickpasshub.QuickPass_Hub.auth.dto.LoginPayload
import jakarta.servlet.http.HttpServletResponse
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Controller
import org.springframework.web.context.request.NativeWebRequest


@Controller
class AuthResolver(
    private val authService: AuthService,
    private val cookieSupport: CookieSupport,
) {

    @MutationMapping
    fun login(
        @Argument email: String,
        @Argument password: String,
        web: NativeWebRequest,
    ) : LoginPayload{

        val tokens: AuthService.Tokens = authService.login(email, password)

        val res : HttpServletResponse  = web.getNativeResponse(HttpServletResponse::class.java)!!
        val cookie = cookieSupport.writeRefreshTokenCookie(
            tokens.refreshToken,
            false, // local은 false, 운영(HTTPS)는 true
            "Lax", // 필요시 Strict 혹은 cross-site면 None (+ secure=true 필수)
            "/"
        )
        res.addHeader(HttpHeaders.SET_COOKIE, cookie.toString())

        // 4. Access 는 바디로 반환
        return LoginPayload(tokens.accessToken)
    }

}