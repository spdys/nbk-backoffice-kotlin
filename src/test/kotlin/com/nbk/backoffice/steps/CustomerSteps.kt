package com.nbk.backoffice.steps

import com.nbk.backoffice.config.DataSeeder
import io.cucumber.java.Before
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import io.cucumber.spring.CucumberContextConfiguration
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CustomerSteps {
    @LocalServerPort
    var port: Int = 0

    @Autowired
    lateinit var testRestTemplate: TestRestTemplate

    @Autowired
    lateinit var dataSeeder: DataSeeder

    private var lastResponse: ResponseEntity<String>? = null
    private var jwt: String? = null
    private val variables = mutableMapOf<String, String>()

    @Before
    fun seedCustomer() {
        dataSeeder.seedUsers()
    }

    @Given("I log in with username {string} and password {string}")
    fun login(username: String, password: String) {
        val url = "http://localhost:$port/auth/login"
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        val body = """{"username":"$username","password":"$password"}"""
        val request = HttpEntity(body, headers)
        val response = testRestTemplate.postForEntity(url, request, String::class.java)
        lastResponse = response

        val tokenRegex = Regex("\"token\":\"([^\"]+)\"")
        jwt = tokenRegex.find(response.body ?: "")?.groupValues?.get(1)
    }

    @When("I make a GET request to {string}")
    fun getRequest(endpoint: String) {
        val finalEndpoint = variables.entries.fold(endpoint) { acc, entry ->
            acc.replace("{${entry.key}}", entry.value)
        }
        val url = "http://localhost:$port$finalEndpoint"
        val headers = HttpHeaders()
        jwt?.let { headers.setBearerAuth(it) }
        val request = HttpEntity<String>(headers)
        lastResponse = testRestTemplate.exchange(url, HttpMethod.GET, request, String::class.java)
    }

    @When("I make a POST request to {string} with body:")
    fun postRequest(endpoint: String, body: String) {
        val finalEndpoint = variables.entries.fold(endpoint) { acc, entry ->
            acc.replace("{${entry.key}}", entry.value)
        }
        val url = "http://localhost:$port$finalEndpoint"
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        jwt?.let { headers.setBearerAuth(it) }
        val request = HttpEntity(body, headers)
        lastResponse = testRestTemplate.postForEntity(url, request, String::class.java)
    }

    @When("I make a PUT request to {string} with body:")
    fun putRequest(endpoint: String, body: String) {
        val finalEndpoint = variables.entries.fold(endpoint) { acc, entry ->
            acc.replace("{${entry.key}}", entry.value)
        }
        val url = "http://localhost:$port$finalEndpoint"
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        jwt?.let { headers.setBearerAuth(it) }
        val request = HttpEntity(body, headers)
        lastResponse = testRestTemplate.exchange(url, HttpMethod.PUT, request, String::class.java)
    }

    @When("I make a DELETE request to {string}")
    fun deleteRequest(endpoint: String) {
        val finalEndpoint = variables.entries.fold(endpoint) { acc, entry ->
            acc.replace("{${entry.key}}", entry.value)
        }
        val url = "http://localhost:$port$finalEndpoint"
        val headers = HttpHeaders()
        jwt?.let { headers.setBearerAuth(it) }
        val request = HttpEntity<String>(headers)
        lastResponse = testRestTemplate.exchange(url, HttpMethod.DELETE, request, String::class.java)
    }

    @When("I extract the ID of customer with name {string} as {string}")
    fun extractCustomerIdByName(customerName: String, variableName: String) {
        val pattern = """"id"\s*:\s*(\d+).*?"customerName"\s*:\s*"$customerName"""".toRegex(RegexOption.DOT_MATCHES_ALL)
        val responseBody = lastResponse?.body ?: ""
        val match = pattern.find(responseBody)
        val id = match?.groups?.get(1)?.value?.toLong()
            ?: throw IllegalStateException("Customer with name $customerName not found in response")
        // Store in a map for placeholder replacement
        variables[variableName] = id.toString()
    }


    @Then("the response status code should be {int}")
    fun statusCode(expected: Int) {
        assertEquals(expected, lastResponse?.statusCode?.value())
    }

    @Then("the response body should contain {string}")
    fun bodyContains(value: String) {
        assertTrue(lastResponse?.body?.contains(value) == true)
    }
}