@dev
Feature: Update product

  Scenario: Validate error when trying to update product with no authorization
    Given the user is logged in
    And the user adds a new product Chess Board of product type games
    And the user sees the newly added product exists
    When the user tries to update the product with no authorization
    Then a 401 response code is returned from the PUT endpoint
    And the error message displayed is No token, authorization denied
    When the user deletes the product
    Then the user sees the message Product removed


  Scenario: Validate error on giving invalid product id for update
    Given the user is logged in
    When the user tries to update an invalid product
    Then a 404 response code is returned from the PUT endpoint
    And the error message displayed is Product not found