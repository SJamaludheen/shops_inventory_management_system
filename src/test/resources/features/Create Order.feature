@dev
Feature: Create order

  Scenario: Validate error on insufficient stock (for 'sell' orders)
    Given the user is logged in
    And the user adds a new product Checkers of product type games
    And the user sees the newly added product exists
    But the user cannot sell any unit of the product
    And a 400 response code is received from the POST endpoint
    And the user sees the error message displayed as Insufficient stock for sale


  Scenario: Validate error error when trying to create order with no authorization
    Given the user is logged in
    And the user adds a new product Scrabble of product type games
    And the user sees the newly added product exists
    When the user tries to create order with no authorization
    Then a 401 response code is received from the POST endpoint
    And the user sees the error message displayed as No token, authorization denied


  Scenario: Validate error on giving invalid product id to create order
    Given the user is logged in
    When the user tries to buy 1 unit of invalid product
    Then a 404 response code is received from the POST endpoint
    And the user sees the error message displayed as Product not found


  Scenario: Validate error on invalid quantity while creating an order - buy product
    Given the user is logged in
    And the user adds a new product Jenga of product type games
    And the user sees the newly added product exists
    When the user tries to buy 0 unit of valid product
    Then a 400 response code is received from the POST endpoint
    And the user sees the error message displayed as Quantity must be a positive number


  Scenario: Validate error on invalid quantity while creating an order - sell product
    Given the user is logged in
    And the user adds a new product Jenga of product type games
    And the user sees the newly added product exists
    When the user tries to sell 0 unit of valid product
    Then a 400 response code is received from the POST endpoint
    And the user sees the error message displayed as Quantity must be a positive number