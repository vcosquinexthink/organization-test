Feature: Infrastructure scenarios, things not purely functional

  Scenario: Users without credentials can not access the system
    When we call the application with wrong credentials
    Then we receive an unauthenticated error code

  Scenario: Monitoring endpoint
    When we call the application management metrics endpoints
    Then we receive a valid metrics report