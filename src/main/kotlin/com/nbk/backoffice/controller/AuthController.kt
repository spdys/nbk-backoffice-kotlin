package com.nbk.backoffice.controller

import com.nbk.backoffice.exceptions.BackOfficeException
import com.nbk.backoffice.repository.UserRepository
import com.nbk.backoffice.service.JwtService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthController(
    private val userRepository: UserRepository,
    private val authenticationManager: AuthenticationManager,
    private val jwtService: JwtService
) {
    @PostMapping("/login")
    fun login(@RequestBody request: AuthRequest): ResponseEntity<AuthResponse> {
        val authToken = UsernamePasswordAuthenticationToken(request.username, request.password)
        authenticationManager.authenticate(authToken)

        val user = userRepository.findByUsername(request.username)
            ?: throw BackOfficeException("Invalid credentials", HttpStatus.BAD_REQUEST)

        val token = jwtService.generateToken(user)

        return ResponseEntity.ok(AuthResponse(token))
    }
}

data class AuthRequest(val username: String, val password: String)
data class AuthResponse(val token: String)
