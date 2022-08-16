Feature: Super Heroes
  Background: Creating a user
    Given User Details



  Scenario: Verify that user is created
    When creating a User
    Then User must  be Created


  Scenario: Verify that the user   cant  create a name
    When creating a user with null name
    Then Name is required error appears

  Scenario: Verify that the user cant create a super name
    When Creating a user with null super name
    Then Super name is required error appears

  Scenario: Verify that the user cant create a profession
    When Creating user with null profession
    Then profession is required error appears


  Scenario: Verify that the user cant create  age
    When Creating user with null age
    Then Age is required error appears

  Scenario: Verify that the user cant create  canFly status
    When Creating user with blank canFly
    Then User must Created

  Scenario: Verify that the user name is updated
    When creating a User
    And updating a user name
    Then User name must be updated

  Scenario: Verify that the user super name is updated
    When creating a User
    And updating super name
    Then User super name must be updated

  Scenario: Verify that the user profession is updated
    When creating a User
    And updating profession
    Then User profession must be updated

  Scenario: Verify that the user age is updated
    When creating a User
    And updating age
    Then User age must be updated

  Scenario: Verify that the user canFly status is updated
    When creating a User
    And updating canFly  status of user
    Then User canFly status must be updated

  Scenario: Verify that the users list is displayed
    When creating a User
    Then List of user must be displayed

  Scenario: Verify that the particular user in the list is displayed
    When creating a User
    Then user with particular id must be displayed

  Scenario: Verify that the user is deleted
    When creating a User
    Then  Deleting a user

  Scenario: Verify that the invalid user is deleted
    When creating a User
    Then Deleting a invalid user

















