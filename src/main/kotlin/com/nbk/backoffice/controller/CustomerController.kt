package com.nbk.backoffice.controller

import com.nbk.backoffice.data.CustomerRequest
import com.nbk.backoffice.data.UserResponse
import com.nbk.backoffice.service.CustomerService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/customers")
class CustomerController(
    private val customerService: CustomerService
) {

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    fun getAll(): ResponseEntity<List<UserResponse>> =
        ResponseEntity.ok(customerService.getAllCustomers())

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    fun add(@RequestBody req: CustomerRequest): ResponseEntity<UserResponse> {
        val created = customerService.createCustomer(req)
        return ResponseEntity.status(HttpStatus.CREATED).body(created)
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody req: CustomerRequest): ResponseEntity<UserResponse> {
        val updated = customerService.updateCustomer(id, req)
        return ResponseEntity.ok(updated)
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Void> {
        customerService.deleteCustomer(id)
        return ResponseEntity.noContent().build()
    }
}