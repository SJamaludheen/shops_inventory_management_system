@dev
  # This is an E2E test to visit all the endpoints of the application in the following order to test and validate:
      # Add a new product
      # Verify the product exists
      # Update the product
      # Verify the product details are updated
      # Buy the product
      # Verify the increase in stock
      # Sell the product
      # Verify the decrease in stock
      # Delete the product
      # Verify product is deleted

Feature: Shops Inventory Management System - E2E test

  Scenario: Validate the e2e user journey where user can get, add and update product, buy and sell product and delete the product
    Given the user is logged in
    And the user can retrieve all available products
    When the user adds a new product Chess Board - size of product type games
    Then the user sees the newly added product exists
    And the product details - name, price, productType and quantity match
    When the user can update the product as Monopoly of product type games
    Then the user sees the product details are updated
    And the product details - name, price, productType and quantity match
    When the user buys 1 unit of the product
    And the user checks the initial stock of the product
    And the user buys 1 unit of the product
    Then the user sees the stock level of the product has now increased by 1 unit
    When the user checks the initial stock of the product
    And the user sells 1 unit of the product
    Then the user sees the stock level of the product has now decreased by 1 unit
    When the user deletes the product
    Then the user sees the message Product removed
    And the user can see the product doesn't exist
    And the user sees the error message displayed is Product not found