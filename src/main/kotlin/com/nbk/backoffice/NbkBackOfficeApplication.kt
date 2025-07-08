package com.nbk.backoffice

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@OpenAPIDefinition(
	info = Info(
		title = "NBK Backoffice API",
		version = "1.0",
		description = "API documentation for managing customers and authentication"
	)
)
@SpringBootApplication
class NbkBackOfficeApplication

fun main(args: Array<String>) {
	runApplication<NbkBackOfficeApplication>(*args)
}
