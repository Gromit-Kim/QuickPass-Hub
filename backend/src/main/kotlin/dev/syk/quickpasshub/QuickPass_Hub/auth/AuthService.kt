package dev.syk.quickpasshub.QuickPass_Hub.auth

import dev.syk.quickpasshub.QuickPass_Hub.users.UsersRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val usersRepository: UsersRepository,
    private val passwordEncoder: PasswordEncoder,
    private val tokenService: TokenService,
) {

    data class Tokens(
        val accessToken: String,
        val refreshToken: String,
    )

    fun login(email: String, rawPassword: String) : Tokens{
        val user = usersRepository.findByEmail(email)?: throw IllegalArgumentException("존재하지 않는 이메일입니다.");

        val matches: Boolean = passwordEncoder.matches(rawPassword, user.password)
        if(!matches){
            throw IllegalArgumentException("비밀번호가 틀렸습니다.")
        }

        val accessToken: String = tokenService.createAccessToken(user.email, roles = listOf(user.role.name))
        val refreshToken: String = tokenService.createRefreshToken(user.email)

        return Tokens(accessToken, refreshToken)
    }

}