package com.nbk.test.data

import com.nbk.test.entity.Gender
import com.nbk.test.entity.Role
import com.nbk.test.entity.UserEntity
import java.time.LocalDate

data class UserResponse(
    val id: Long,
    val username: String,
    val customerNumber: Int?,
    val customerName: String?,
    val dateOfBirth: LocalDate?,
    val gender: Gender?,
    val role: Role
)


fun UserEntity.toDto() = UserResponse(
    id = id,
    username = username,
    customerNumber = customerNumber,
    customerName = customerName,
    dateOfBirth = dateOfBirth,
    gender = gender,
    role = role
)