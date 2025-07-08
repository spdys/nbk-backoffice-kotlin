package com.nbk.backoffice.data

import com.nbk.backoffice.entity.Gender
import java.time.LocalDate

data class CustomerRequest(
    val username: String?,
    val password: String?,
    val customerName: String?,
    val dateOfBirth: LocalDate?,
    val gender: Gender?
)
