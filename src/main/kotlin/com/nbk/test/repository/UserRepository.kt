package com.nbk.test.repository

import com.nbk.test.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<UserEntity, Long> {
    fun findByUsername(username: String): UserEntity?
    fun findByCustomerNumber(customerNumber: Int): UserEntity?
}
