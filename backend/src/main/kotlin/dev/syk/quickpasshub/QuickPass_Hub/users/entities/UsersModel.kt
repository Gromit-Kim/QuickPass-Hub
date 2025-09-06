package dev.syk.quickpasshub.QuickPass_Hub.users.entities

import dev.syk.quickpasshub.QuickPass_Hub.common.BaseModel
import dev.syk.quickpasshub.QuickPass_Hub.users.constants.RolesEnum
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint

@Entity
@Table(name = "users", uniqueConstraints = [UniqueConstraint(columnNames = ["email"])])
open class UsersModel(
    @Column(nullable = false)
    val name: String,

    @Column(nullable = false, unique = true)
    val email: String,

    @Column(nullable = false, length = 255)
    val password: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    val role: RolesEnum,
) : BaseModel()