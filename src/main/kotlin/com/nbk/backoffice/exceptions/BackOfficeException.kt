package com.nbk.backoffice.exceptions

import org.springframework.http.HttpStatus

open class BackOfficeException(
    override val message: String = "Internal Server Error",
    open val httpStatus: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR,
    override val cause: Throwable? = null
) : RuntimeException(message, cause)