Feature: Add new pet to owner
  Scenario: Adding a pet for hamid
    Given There is an owner called "Sina"
    When A new Pet is set for the owner
    Then The owner has the new pet
