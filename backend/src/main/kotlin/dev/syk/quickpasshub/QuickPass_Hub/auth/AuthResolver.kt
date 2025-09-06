package dev.syk.quickpasshub.QuickPass_Hub.auth

import dev.syk.quickpasshub.QuickPass_Hub.auth.dto.LoginPayload
import dev.syk.quickpasshub.QuickPass_Hub.auth.dto.RegisterInput
import jakarta.servlet.http.HttpServletResponse
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseCookie
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

    @MutationMapping
    fun register(@Argument input: RegisterInput, web: NativeWebRequest) : LoginPayload{
        val tokens : AuthService.Tokens = authService.register(input)

        val res: HttpServletResponse = web.getNativeResponse(HttpServletResponse::class.java)!!
        val cookie: ResponseCookie = cookieSupport.writeRefreshTokenCookie(
            token   = tokens.refreshToken,
            secure  = false,   // 로컬: false / 운영(HTTPS): true
            sameSite= "Lax",
            path    = "/"
        )
        res.addHeader(HttpHeaders.SET_COOKIE, cookie.toString())

        return LoginPayload(accessToken = tokens.accessToken)
    }

}