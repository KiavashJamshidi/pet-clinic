Feature: Find Owner

  Scenario: Found existing owner with id 1
    Given There is an owner with id "1"
    When Tried to find the owner
    Then The owner is found properly
