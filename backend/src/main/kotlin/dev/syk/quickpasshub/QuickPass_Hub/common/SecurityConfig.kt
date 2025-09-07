package dev.syk.quickpasshub.QuickPass_Hub.common

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig{

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain{
        http
            .csrf{
                it.disable()
            }
            .authorizeHttpRequests {
                it.requestMatchers(HttpMethod.POST, "/graphql").permitAll()
                it.anyRequest().authenticated()
            }
            // .addFilterBefore()
            .httpBasic {  it.disable() }
            .formLogin {  it.disable() }
        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()
}