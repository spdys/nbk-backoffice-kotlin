package com.nbk.backoffice.service

import com.nbk.backoffice.data.CustomerRequest
import com.nbk.backoffice.data.UserResponse
import com.nbk.backoffice.data.toDto
import com.nbk.backoffice.entity.Role
import com.nbk.backoffice.entity.UserEntity
import com.nbk.backoffice.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class CustomerService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {

    fun getAllCustomers(): List<UserResponse> =
        userRepository.findAllCustomersOrderById().map { it.toDto() }

    fun createCustomer(req: CustomerRequest): UserResponse {
        val customerNumber = generateCustomerNumber()
        val customer = UserEntity(
            username = req.username,
            password = passwordEncoder.encode(req.password),
            role = Role.CUSTOMER,
            customerNumber = customerNumber,
            customerName = req.customerName,
            dateOfBirth = req.dateOfBirth,
            gender = req.gender
        )
        return userRepository.save(customer).toDto()
    }

    fun updateCustomer(id: Long, req: CustomerRequest): UserResponse {
        val existing = userRepository.findById(id).orElseThrow {
            RuntimeException("Customer not found")
        }
        if (existing.role != Role.CUSTOMER) {
            throw RuntimeException("User is not a customer")
        }

        val updated = existing.copy(
            username = req.username,
            password = passwordEncoder.encode(req.password),
            customerName = req.customerName,
            dateOfBirth = req.dateOfBirth,
            gender = req.gender
        )

        return userRepository.save(updated).toDto()
    }

    fun deleteCustomer(id: Long) {
        val existing = userRepository.findById(id).orElseThrow {
            RuntimeException("Customer not found")
        }
        if (existing.role != Role.CUSTOMER) {
            throw RuntimeException("User is not a customer")
        }
        userRepository.deleteById(id)
    }

    private fun generateCustomerNumber(): Int {
        var customerNumber: Int
        do { customerNumber = Random.nextInt(100_000_000, 1_000_000_000)
        } while (userRepository.findByCustomerNumber(customerNumber) != null)
        return customerNumber
    }
}
