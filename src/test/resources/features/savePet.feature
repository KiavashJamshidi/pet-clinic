Feature: Save Pet
  Scenario: The owner's pet is saved
    Given There is a pet owner called "Sina"
    When He performs save pet service to add a pet to his list
    Then The pet is saved successfully
