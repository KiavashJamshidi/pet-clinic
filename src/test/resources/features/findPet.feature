Feature: Find Pet
  Scenario: found existing pet with id 1
    Given There is an owner
    And There is a pet with id "1"
    When Tried to find the pet
    Then The pet is found successfully
