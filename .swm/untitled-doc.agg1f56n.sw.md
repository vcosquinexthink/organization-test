---
title: Untitled doc
---
# Introduction

This document will walk you through the implementation of the Organization Controller in the organization-app.

The Organization Controller is responsible for handling HTTP requests related to the organization and its employees.

We will cover:

1. The purpose of the Organization Controller.
2. The constructor and dependency injection.
3. The endpoints for setting and getting organization data.
4. The endpoint for retrieving employee management hierarchy.

# Purpose of the Organization Controller

The Organization Controller is designed to handle HTTP requests for managing organization data and employee hierarchies. It uses the <SwmToken path="/organization-app/src/main/java/com/company/organization/rest/OrganizationController.java" pos="21:5:5" line-data="    private final OrganizationService organization;">`OrganizationService`</SwmToken> to perform the necessary operations.

# Constructor and dependency injection

<SwmSnippet path="/organization-app/src/main/java/com/company/organization/rest/OrganizationController.java" line="21">

---

The constructor of the Organization Controller injects the <SwmToken path="/organization-app/src/main/java/com/company/organization/rest/OrganizationController.java" pos="21:5:5" line-data="    private final OrganizationService organization;">`OrganizationService`</SwmToken> dependency. This allows the controller to use the service for its operations.

```
    private final OrganizationService organization;

    public OrganizationController(final OrganizationService organization) {
        this.organization = organization;
    }
```

---

</SwmSnippet>

# Setting organization data

<SwmSnippet path="/organization-app/src/main/java/com/company/organization/rest/OrganizationController.java" line="27">

---

The <SwmToken path="/organization-app/src/main/java/com/company/organization/rest/OrganizationController.java" pos="28:5:5" line-data="    public void setOrganization(@RequestBody final Map&lt;String, String&gt; organizationReceived) {">`setOrganization`</SwmToken> method handles POST requests to the <SwmPath>[organization-acceptance-tests/src/test/java/com/company/organization/](/organization-acceptance-tests/src/test/java/com/company/organization/)</SwmPath> endpoint. It receives organization data in JSON format and logs the action. The data is then passed to the <SwmToken path="/organization-app/src/main/java/com/company/organization/rest/OrganizationController.java" pos="21:5:5" line-data="    private final OrganizationService organization;">`OrganizationService`</SwmToken> to add employees.

```
    @PostMapping(value = "/organization", consumes = "application/json")
    public void setOrganization(@RequestBody final Map<String, String> organizationReceived) {
        log.info("OrganizationController::setOrganization");
        organization.addEmployees(organizationReceived);
    }
```

---

</SwmSnippet>

# Getting organization data

<SwmSnippet path="/organization-app/src/main/java/com/company/organization/rest/OrganizationController.java" line="33">

---

The <SwmToken path="/organization-app/src/main/java/com/company/organization/rest/OrganizationController.java" pos="34:11:11" line-data="    public Map&lt;String, Map&gt; getOrganization() {">`getOrganization`</SwmToken> method handles GET requests to the <SwmPath>[organization-acceptance-tests/src/test/java/com/company/organization/](/organization-acceptance-tests/src/test/java/com/company/organization/)</SwmPath> endpoint. It logs the action and retrieves the root of the organization hierarchy. The hierarchy is then transformed using the <SwmToken path="/organization-app/src/main/java/com/company/organization/rest/OrganizationController.java" pos="36:3:3" line-data="        return downstreamHierarchy(organization.getRoot().map(List::of).orElse(List.of()));">`downstreamHierarchy`</SwmToken> method and returned as a JSON response.

```
    @GetMapping(value = "/organization", produces = "application/json")
    public Map<String, Map> getOrganization() {
        log.info("OrganizationController::getOrganization");
        return downstreamHierarchy(organization.getRoot().map(List::of).orElse(List.of()));
    }
```

---

</SwmSnippet>

# Retrieving employee management hierarchy

<SwmSnippet path="/organization-app/src/main/java/com/company/organization/rest/OrganizationController.java" line="39">

---

The <SwmToken path="/organization-app/src/main/java/com/company/organization/domain/Organization.java" pos="24:8:8" line-data="    public Optional&lt;Employee&gt; getEmployee(final String employeeName) {">`getEmployee`</SwmToken> method handles GET requests to the <SwmToken path="/organization-app/src/main/java/com/company/organization/rest/OrganizationController.java" pos="39:9:18" line-data="    @GetMapping(value = &quot;/organization/employee/{employeeName}/management&quot;, produces = &quot;application/json&quot;)">`/organization/employee/{employeeName}/management`</SwmToken> endpoint. It logs the action and retrieves the management hierarchy for the specified employee. The hierarchy is then transformed using the <SwmToken path="/organization-app/src/main/java/com/company/organization/rest/HierarchyRepresentation.java" pos="21:13:13" line-data="    public static Map&lt;String, Map&gt; upstreamHierarchy(final Employee employee) {">`upstreamHierarchy`</SwmToken> method and returned as a JSON response.

```
    @GetMapping(value = "/organization/employee/{employeeName}/management", produces = "application/json")
    public Map<String, Map> getEmployee(@PathVariable(value = "employeeName") String employeeName) {
        log.info("OrganizationController::getEmployee");
        // todo.map(::upstreamHierarchy);
        return upstreamHierarchy(organization.getEmployee(employeeName).get());
    }
}
```

---

</SwmSnippet>

This concludes the walkthrough of the Organization Controller implementation. The controller is designed to handle organization data and employee hierarchies efficiently using the <SwmToken path="/organization-app/src/main/java/com/company/organization/rest/OrganizationController.java" pos="21:5:5" line-data="    private final OrganizationService organization;">`OrganizationService`</SwmToken>.

<SwmMeta version="3.0.0" repo-id="Z2l0aHViJTNBJTNBb3JnYW5pemF0aW9uLXRlc3QlM0ElM0F2Y29zcXVpbmV4dGhpbms=" repo-name="organization-test"><sup>Powered by [Swimm](https://app.swimm.io/)</sup></SwmMeta>
