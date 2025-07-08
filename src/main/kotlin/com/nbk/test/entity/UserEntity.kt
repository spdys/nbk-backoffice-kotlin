package com.nbk.test.entity

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "users")
data class UserEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false, unique = true)
    val username: String = "",

    @Column(nullable = false)
    val password: String = "",

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val role: Role = Role.CUSTOMER,

    @Column(unique = true)
    val customerNumber: Int? = null,

    @Column(length = 255)
    val customerName: String? = null,

    val dateOfBirth: LocalDate? = null,

    @Enumerated(EnumType.STRING)
    @Column(length = 1)
    val gender: Gender? = null
)

enum class Role { ADMIN, CUSTOMER }

enum class Gender { F, M }