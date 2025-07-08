package com.nbk.backoffice.config

import com.nbk.backoffice.exceptions.BackOfficeException
import com.nbk.backoffice.exceptions.FailureResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(BackOfficeException::class)
    fun handleBackOfficeException(ex: BackOfficeException): ResponseEntity<FailureResponse> {
        val errorResponse = FailureResponse(
            timestamp = LocalDateTime.now().toString(),
            status = ex.httpStatus.value(),
            error = ex.httpStatus.reasonPhrase,
            message = ex.message,
        )
        return ResponseEntity.status(ex.httpStatus).body(errorResponse)
}
    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgument(ex: IllegalArgumentException): ResponseEntity<FailureResponse> {
        val errorResponse = FailureResponse(
            timestamp = LocalDateTime.now().toString(),
            status = HttpStatus.BAD_REQUEST.value(),
            error = HttpStatus.BAD_REQUEST.reasonPhrase,
            message = ex.message ?: "Illegal Argument"
        )
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
    }
}