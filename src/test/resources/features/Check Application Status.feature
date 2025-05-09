@dev
  Feature: Application status

    Scenario: Check status of the application
      Given the user checks for application status
      Then the application status returned is OK
      And the DB status returned is Connected