package dev.syk.quickpasshub.QuickPass_Hub.auth.dto

import dev.syk.quickpasshub.QuickPass_Hub.users.constants.RolesEnum

data class RegisterInput(
    val name: String,
    val email: String,
    val password: String,
    val role: RolesEnum = RolesEnum.USER,
)
