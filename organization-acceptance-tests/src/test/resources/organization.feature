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
      {"Jonas":[{"Sophie":[{"Nick":[{"Barbara":[]},{"Pete":[]}]}]}]}
    """

  Scenario: Organization hierarchy should prevent multiple roots from being added
    When we try to set the following organization hierarchy:
      | employee | supervisor |
      | Pete     | Nick       |
      | Barbara  | Nick       |
      | Nick     | Sophie     |
      | Sophie   | Jonas      |
      | Alma     | Carl       |
    Then application rejects it with the following error message "Error: More than one root was added: [Jonas, Carl]"

  Scenario: Users without credentials can not access the system
    When we call the application with wrong credentials
    Then we receive an unauthenticated code