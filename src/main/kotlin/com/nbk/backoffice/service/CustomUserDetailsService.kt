package com.nbk.backoffice.service

import com.nbk.backoffice.exceptions.BackOfficeException
import com.nbk.backoffice.repository.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.security.core.userdetails.*
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByUsername(username)
            ?: throw BackOfficeException("User not found", HttpStatus.NOT_FOUND)

        return User.builder()
            .username(user.username)
            .password(user.password)
            .roles(user.role.toString())
            .build()
    }
}