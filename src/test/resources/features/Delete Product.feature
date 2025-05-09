@dev
Feature: Delete product

  Scenario: Validate error when trying to delete product with no authorization
    Given the user is logged in
    And the user adds a new product Scrabble of product type games
    And the user sees the newly added product exists
    When the user tries to delete the product with no authorization
    Then a 401 response code is returned from the DELETE endpoint
    And the user sees the message No token, authorization denied


  Scenario: Validate error on giving invalid product id for delete
    Given the user is logged in
    When the user tries to delete an invalid product
    Then a 404 response code is returned from the DELETE endpoint
    And the user sees the message Product not found


  Scenario Outline: Delete a specific product id
    Given the user is logged in
    When the user deletes given product <product_id>
    Examples:
      | product_id                           |
      | 1801b1f0-1779-4a15-a871-a91555668276 |
      | d5c4afd4-20d9-45e5-ae08-44372d34cbfe |