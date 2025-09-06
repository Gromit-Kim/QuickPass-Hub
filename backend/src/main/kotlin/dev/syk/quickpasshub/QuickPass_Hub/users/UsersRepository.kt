package dev.syk.quickpasshub.QuickPass_Hub.users

import dev.syk.quickpasshub.QuickPass_Hub.users.entities.UsersModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UsersRepository : JpaRepository<UsersModel, Long>{
    fun findByEmail(email: String) : UsersModel?
}