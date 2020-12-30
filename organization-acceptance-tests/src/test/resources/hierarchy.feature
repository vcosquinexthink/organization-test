Feature: Feature

  Scenario: Hierarchy scenario
    Given we have set the following company hierarchy:
      | employee | supervisor |
      | Pete     | Nick       |
      | Barbara  | Nick       |
      | Nick     | Sophie     |
      | Sophie   | Jonas      |
    When we check the company hierarchy
    Then "Nick" is the supervisor of "Pete"
    And "Nick" is the supervisor of "Barbara"
    And "Sophie" is the supervisor of "Nick"
    And "Jonas" is the supervisor of "Sophie"