---
title: Untitled doc (2)
---
<SwmSnippet path="/organization-app/src/main/java/com/company/organization/domain/OrganizationService.java" line="1">

---

&nbsp;

```java
package com.company.organization.domain;


import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Map;
import java.util.Optional;

@Component
public class OrganizationService {

    private final Organization organization;

    public OrganizationService(final Organization organization) {
        this.organization = organization;
    }

    public Optional<Employee> getRoot() {
        return organization.getRoot();
    }

    public Optional<Employee> getEmployee(final String employeeName) {
        return organization.getEmployee(employeeName);
    }

    @Transactional
    public void addEmployees(final Map<String, String> newEmployees) {
        newEmployees.forEach(organization::addEmployee);
        organization.verifySingleRoot();
    }
}

```

---

</SwmSnippet>

<SwmMeta version="3.0.0" repo-id="Z2l0aHViJTNBJTNBb3JnYW5pemF0aW9uLXRlc3QlM0ElM0F2Y29zcXVpbmV4dGhpbms=" repo-name="organization-test"><sup>Powered by [Swimm](https://app.swimm.io/)</sup></SwmMeta>
