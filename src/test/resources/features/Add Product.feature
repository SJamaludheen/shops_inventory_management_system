@dev
Feature: Add product

  Scenario Outline: Validate error on missing required fields - name, price
    Given the user is logged in
    When the user adds a new product with missing <field_value>
    Then a 400 response code is returned from the POST endpoint
    And the message displayed is Validation failed
    Examples:
      | field_value |
      | name        |
      | price       |


  Scenario: Validate error on adding the same product twice
    Given the user is logged in
    When the user adds a new product Chess Board of product type games
    Then the user sees the newly added product exists
    And the product details - name, price, productType and quantity match
    When the user adds the product Chess Board of product type games again
    Then a 400 response code is returned from the POST endpoint
    And the message displayed is Product with this name and type already exists


  Scenario: Validate error on giving invalid price
    Given the user is logged in
    When the user adds a new product with invalid price
    Then a 400 response code is returned from the POST endpoint
    And the message displayed is Price must be greater than 0


  Scenario: Validate unauthorized access to POST products endpoint
    When the user adds a new product with no authorization
    Then a 401 response code is returned from the POST endpoint
    And the message displayed is No token, authorization denied