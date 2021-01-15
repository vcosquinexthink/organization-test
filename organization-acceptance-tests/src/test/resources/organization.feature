Feature: Organization scenarios, these scenarios should cover the basic, most critical functionalities for organization management.

  Scenario: Organization hierarchy should be correctly configured
    Given we have set the following organization hierarchy:
      | employee | supervisor |
      | Pete     | Nick       |
      | Barbara  | Nick       |
      | Nick     | Sophie     |
      | Sophie   | Jonas      |
    When we check the organization hierarchy
    Then organization hierarchy is:
    """
      {"Jonas":{"Sophie":{"Nick":{"Pete":{},"Barbara":{}}}}}
    """

  Scenario: Application should return the management chain for an employee
    When we check the management chain for "Barbara"
    Then management chain is:
    """
      {"Barbara":{"Nick":{"Sophie":{"Jonas":{}}}}}
    """

  Scenario: Organization hierarchy should prevent multiple roots from being added
    When we try to add the following organization hierarchy:
      | employee | supervisor |
      | Alma     | Carl       |
    Then application rejects it with the following error message "Error: More than one root was added: [Jonas, Carl]"

  Scenario: Organization hierarchy should prevent cyclic dependencies
    When we try to add the following organization hierarchy:
      | employee | supervisor |
      | Sophie   | Nick       |
    Then application rejects it with the following error message "Error: There is a cyclic dependency in employee [Nick]"

  Scenario: Users without credentials can not access the system
    When we call the application with wrong credentials
    Then we receive an unauthenticated error code