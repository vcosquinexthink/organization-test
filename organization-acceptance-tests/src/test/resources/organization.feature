Feature: Organization scenarios, these scenarios should cover the basic, most critical functionalities for organization management.

  Scenario: Organization hierarchy should be correctly configured
    Given we have set the following organization hierarchy:
      | employee | supervisor |
      | Carl     | Amanda     |
      | Samuel   | Amanda     |
      | Amanda   | Olga       |
      | Olga     | Jane       |
    When we check the organization hierarchy
    Then organization hierarchy is:
    """
      {"Jane":{"Olga":{"Amanda":{"Samuel":{},"Carl":{}}}}}
    """

  Scenario: Application should return the management chain for an employee
    When we check the management chain for "Samuel"
    Then management chain is:
    """
      {"Samuel":{"Amanda":{"Olga":{"Jane":{}}}}}
    """

  Scenario: Organization hierarchy should prevent multiple roots from being added
    When we try to add the following organization hierarchy:
      | employee | supervisor |
      | Alma     | Mike       |
    Then application rejects it with the following error message "Error: More than one root was added: [Jane, Mike]"

  Scenario: Organization hierarchy should prevent cyclic dependencies
    When we try to add the following organization hierarchy:
      | employee | supervisor |
      | Olga     | Amanda     |
    Then application rejects it with the following error message "Error: There is a cyclic dependency in employee [Amanda]"
