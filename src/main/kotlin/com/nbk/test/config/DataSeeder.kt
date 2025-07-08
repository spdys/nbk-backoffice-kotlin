package com.nbk.test.config

import com.nbk.test.entity.Gender
import com.nbk.test.entity.Role
import com.nbk.test.entity.UserEntity
import com.nbk.test.repository.UserRepository
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import kotlin.random.Random

@Component
class DataSeeder(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {
    fun seedUsers(): List<UserEntity> {
        val defaultPassword = passwordEncoder.encode("Password123")

        val usersToSeed = listOf(
            UserEntity(
                username = "admin",
                password = passwordEncoder.encode("admin123"),
                role = Role.ADMIN
            ),
            UserEntity(
                username = "ali1999",
                password = defaultPassword,
                role = Role.CUSTOMER,
                customerNumber = generateCustomerNumber(),
                customerName = "Ali Al Ahmad",
                dateOfBirth = LocalDate.parse("1999-04-15"),
                gender = Gender.M
            ),
            UserEntity(
                username = "sara2000",
                password = defaultPassword,
                role = Role.CUSTOMER,
                customerNumber = generateCustomerNumber(),
                customerName = "Sara Al Kazemi",
                dateOfBirth = LocalDate.parse("2000-11-22"),
                gender = Gender.F
            ),
            UserEntity(
                username = "yousef2001",
                password = defaultPassword,
                role = Role.CUSTOMER,
                customerNumber = generateCustomerNumber(),
                customerName = "Yousef Al Mutairi",
                dateOfBirth = LocalDate.parse("2001-06-30"),
                gender = Gender.M
            ),
            UserEntity(
                username = "fatima2002",
                password = defaultPassword,
                role = Role.CUSTOMER,
                customerNumber = generateCustomerNumber(),
                customerName = "Fatima Al Otaibi",
                dateOfBirth = LocalDate.parse("2002-02-10"),
                gender = Gender.F
            ),
            UserEntity(
                username = "mohammed2003",
                password = defaultPassword,
                role = Role.CUSTOMER,
                customerNumber = generateCustomerNumber(),
                customerName = "Mohammed Al Anzi",
                dateOfBirth = LocalDate.parse("2003-09-05"),
                gender = Gender.M
            )
        )

        return usersToSeed.map { user ->
            val existing = userRepository.findByUsername(user.username)
            if (existing == null) {
                userRepository.save(user)
            } else {
                val updated = existing.copy(
                    password = user.password,
                    role = user.role,
                    customerNumber = user.customerNumber,
                    customerName = user.customerName,
                    dateOfBirth = user.dateOfBirth,
                    gender = user.gender
                )
                userRepository.save(updated)
            }
        }
    }

    @EventListener(ApplicationReadyEvent::class)
    @Transactional
    fun seedInitialData() {
        val users = seedUsers()
        for (user in users) { println("Updated user: ${user.username}") }
    }

    private fun generateCustomerNumber(): Int {
        var customerNumber: Int
        do { customerNumber = Random.nextInt(100_000_000, 1_000_000_000)
        } while (userRepository.findByCustomerNumber(customerNumber) != null)
        return customerNumber
    }
}
