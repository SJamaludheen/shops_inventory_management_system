@dev
Feature: Get stock

  Scenario: Validate unauthorized access to GET stock endpoint
    When the user checks the stock of a product with no authorization
    Then a 401 response code is returned from the GET endpoint
    And the message returned is No token, authorization denied


  Scenario: Validate error on giving invalid product id to get stock
    Given the user is logged in
    When the user tries to fetch stock of an invalid product
    Then a 404 response code is returned from the GET endpoint
    And the message returned is No orders found for this product