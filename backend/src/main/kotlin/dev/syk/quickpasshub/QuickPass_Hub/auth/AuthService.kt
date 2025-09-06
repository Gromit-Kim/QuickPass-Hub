package dev.syk.quickpasshub.QuickPass_Hub.auth

import dev.syk.quickpasshub.QuickPass_Hub.auth.dto.RegisterInput
import dev.syk.quickpasshub.QuickPass_Hub.users.UsersRepository
import dev.syk.quickpasshub.QuickPass_Hub.users.entities.UsersModel
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

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

    @Transactional
    fun register(input: RegisterInput) : Tokens {
        val exists : Boolean = usersRepository.existsByEmail(input.email)
        if(exists){
            throw IllegalArgumentException("이미 가입된 이메일입니다.")
        }

        // 2. 이메일 형식 검증/실존 확인은 개발 단계에선 생략 가능

        // 운영시에는 클라이언트가 role을 "ADMIN"으로 보내지 못하도록 서버에서 강제 USER 지정을 권장함.
        val passwordHash : String = passwordEncoder.encode(input.password)

        val savedUser : UsersModel = try{
            usersRepository.save(UsersModel(input.name, input.email, passwordHash, input.role))
        }catch (e: DataIntegrityViolationException){
            // 동시성으로 인해 유니크 제약 위반이 터질 수 있으니 보강
            throw IllegalArgumentException("흠...")
        }

        val accessToken: String = tokenService.createAccessToken(savedUser.email, listOf(savedUser.role.name))
        val refreshToken: String = tokenService.createRefreshToken(savedUser.email)

        return Tokens(accessToken, refreshToken)
    }

}