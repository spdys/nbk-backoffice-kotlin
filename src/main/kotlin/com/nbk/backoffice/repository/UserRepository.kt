package com.nbk.backoffice.repository

import com.nbk.backoffice.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.data.jpa.repository.Query

@Repository
interface UserRepository : JpaRepository<UserEntity, Long> {
    fun findByUsername(username: String): UserEntity?
    fun findByCustomerNumber(customerNumber: Int): UserEntity?

    @Query("SELECT u FROM UserEntity u WHERE u.role = com.nbk.backoffice.entity.Role.CUSTOMER ORDER BY u.id ASC")
    fun findAllCustomersOrderById(): List<UserEntity>
}
