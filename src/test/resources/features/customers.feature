Feature: Customer management

  Scenario: Admin logs in successfully
    When I make a POST request to "/auth/login" with body:
    """
    {
      "username": "admin",
      "password": "admin123"
    }
    """
    Then the response status code should be 200
    And the response body should contain "token"

  Scenario: Admin fetches all customers
    Given I log in with username "admin" and password "admin123"
    When I make a GET request to "/customers"
    Then the response status code should be 200
    And the response body should contain "Ali Al Ahmad"

  Scenario: Admin creates a new customer
    Given I log in with username "admin" and password "admin123"
    When I make a POST request to "/customers" with body:
    """
    {
      "username": "newcustomer",
      "password": "pass123",
      "customerName": "New Customer",
      "dateOfBirth": "2001-01-01",
      "gender": "F"
    }
    """
    Then the response status code should be 201
    And the response body should contain "newcustomer"

  Scenario: Admin updates an existing customer
    Given I log in with username "admin" and password "admin123"
    When I make a GET request to "/customers"
    Then the response status code should be 200
    And I extract the ID of customer with name "Ali Al Ahmad" as "capturedCustomerId"
    When I make a PUT request to "/customers/{capturedCustomerId}" with body:
    """
    {
      "username": "ali_updated",
      "password": "newpass123",
      "customerName": "Ali Al Ahmad Updated",
      "dateOfBirth": "1999-04-15",
      "gender": "M"
    }
    """
    Then the response status code should be 200
    And the response body should contain "ali_updated"

  Scenario: Admin deletes an existing customer
    Given I log in with username "admin" and password "admin123"
    When I make a GET request to "/customers"
    Then the response status code should be 200
    And I extract the ID of customer with name "Ali Al Ahmad" as "capturedCustomerId"
    When I make a DELETE request to "/customers/{capturedCustomerId}"
    Then the response status code should be 204
