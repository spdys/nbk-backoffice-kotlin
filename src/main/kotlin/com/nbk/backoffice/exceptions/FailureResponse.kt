package com.nbk.backoffice.exceptions

data class FailureResponse(
    val timestamp: String,
    val status: Int,
    val error: String,
    val message: String
)