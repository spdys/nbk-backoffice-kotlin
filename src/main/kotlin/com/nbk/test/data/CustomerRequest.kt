package com.nbk.test.data

import com.nbk.test.entity.Gender
import java.time.LocalDate

data class CustomerRequest(
    val username: String,
    val password: String,
    val customerName: String,
    val dateOfBirth: LocalDate,
    val gender: Gender
)
