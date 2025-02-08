---
title: Untitled doc (3)
---
# Introduction

This document will walk you through the organization domain implementation in the organization-app.

The organization domain is responsible for managing the hierarchical structure of employees within an organization.

We will cover:

1. Initialization of the Organization class.
2. Methods to retrieve employees.
3. Adding employees and ensuring no cyclic dependencies.
4. Verification of a single root in the organization.

# Initialization of the Organization class

<SwmSnippet path="/organization-app/src/main/java/com/company/organization/domain/Organization.java" line="11">

---

The <SwmToken path="/organization-app/src/main/java/com/company/organization/domain/Organization.java" pos="12:4:4" line-data="public class Organization {">`Organization`</SwmToken> class is initialized with an <SwmToken path="/organization-app/src/main/java/com/company/organization/domain/Organization.java" pos="14:5:5" line-data="    private final EmployeeRepository employeeRepository;">`EmployeeRepository`</SwmToken> to manage employee data. This repository is crucial for all operations within the organization domain.

```
@Component
public class Organization {

    private final EmployeeRepository employeeRepository;

    public Organization(final EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }
```

---

</SwmSnippet>

# Methods to retrieve employees

The <SwmToken path="/organization-app/src/main/java/com/company/organization/domain/Organization.java" pos="12:4:4" line-data="public class Organization {">`Organization`</SwmToken> class provides methods to retrieve the root employee and any employee by name.

<SwmSnippet path="/organization-app/src/main/java/com/company/organization/domain/Organization.java" line="20">

---

To get the root employee:

```
    public Optional<Employee> getRoot() {
        return employeeRepository.findRoots().stream().findAny();
    }
```

---

</SwmSnippet>

<SwmSnippet path="/organization-app/src/main/java/com/company/organization/domain/Organization.java" line="24">

---

To get an employee by name:

```
    public Optional<Employee> getEmployee(final String employeeName) {
        return employeeRepository.findByNameIs(employeeName);
    }
```

---

</SwmSnippet>

# Adding employees and ensuring no cyclic dependencies

<SwmSnippet path="/organization-app/src/main/java/com/company/organization/domain/Organization.java" line="28">

---

The <SwmToken path="/organization-app/src/main/java/com/company/organization/domain/Organization.java" pos="28:5:5" line-data="    public void addEmployee(final String employeeName, final String managerName) {">`addEmployee`</SwmToken> method adds a new employee under a specified manager. It ensures that the employee and manager are correctly linked and checks for cyclic dependencies to maintain a valid hierarchy.

```
    public void addEmployee(final String employeeName, final String managerName) {
        final var employee = employeeRepository.findByNameOrCreate(employeeName);
        final var manager = employeeRepository.findByNameOrCreate(managerName);
        employee.setManager(manager);
        manager.addManaged(employee);
        checkCyclicDep(employee, employee);
        employeeRepository.save(employee);
    }
```

---

</SwmSnippet>

<SwmSnippet path="/organization-app/src/main/java/com/company/organization/domain/Organization.java" line="45">

---

The <SwmToken path="/organization-app/src/main/java/com/company/organization/domain/Organization.java" pos="45:5:5" line-data="    private void checkCyclicDep(final Employee employee, final Employee self) {">`checkCyclicDep`</SwmToken> method is used within <SwmToken path="/organization-app/src/main/java/com/company/organization/domain/Organization.java" pos="28:5:5" line-data="    public void addEmployee(final String employeeName, final String managerName) {">`addEmployee`</SwmToken> to detect and prevent cyclic dependencies in the employee hierarchy.

```
    private void checkCyclicDep(final Employee employee, final Employee self) {
        if (!employee.isRoot()) {
            employee.getManager().ifPresent(manager -> {
                if (manager.equals(self)) {
                    throw new IllegalOrganizationException(format("Error: There is a cyclic dependency in employee [%s]", employee.getName()));
                }
                checkCyclicDep(manager, employee);
            });
        }
    }
}
```

---

</SwmSnippet>

# Verification of a single root in the organization

<SwmSnippet path="/organization-app/src/main/java/com/company/organization/domain/Organization.java" line="37">

---

The <SwmToken path="/organization-app/src/main/java/com/company/organization/domain/Organization.java" pos="37:5:5" line-data="    public void verifySingleRoot() {">`verifySingleRoot`</SwmToken> method ensures that there is only one root employee in the organization. If more than one root is found, it throws an <SwmToken path="/organization-app/src/main/java/com/company/organization/domain/Organization.java" pos="49:5:5" line-data="                    throw new IllegalOrganizationException(format(&quot;Error: There is a cyclic dependency in employee [%s]&quot;, employee.getName()));">`IllegalOrganizationException`</SwmToken>.

```
    public void verifySingleRoot() {
        if (employeeRepository.countRoots() > 1) {
            final var roots = employeeRepository.findRoots().stream().collect(toList()).stream()
                .map(Employee::getName).collect(toList());
            throw new IllegalOrganizationException("Error: More than one root was added: " + roots);
        }
    }
```

---

</SwmSnippet>

This concludes the walkthrough of the organization domain implementation. Each method and check ensures the integrity and proper management of the employee hierarchy within the organization.

<SwmMeta version="3.0.0" repo-id="Z2l0aHViJTNBJTNBb3JnYW5pemF0aW9uLXRlc3QlM0ElM0F2Y29zcXVpbmV4dGhpbms=" repo-name="organization-test"><sup>Powered by [Swimm](https://app.swimm.io/)</sup></SwmMeta>
