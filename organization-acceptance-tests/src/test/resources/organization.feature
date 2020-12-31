Feature: Organization scenarios, these scenarios should cover the basic, most critical functionalities for organization management.

  Scenario: Organization hierarchy should be correctly configured
    Given we have set the following organization hierarchy:
      | employee | supervisor |
      | Pete     | Nick       |
      | Barbara  | Nick       |
      | Nick     | Sophie     |
      | Sophie   | Jonas      |
    When we check the organization hierarchy
    Then "Pete" is supervised by "Nick"
     And "Barbara" is supervised by "Nick"
     And "Nick" is supervised by "Sophie"
     And "Sophie" is supervised by "Jonas"