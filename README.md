# Prerequisites
Things you need to have installed
* java 11 (install via SDKMan)
* docker

# Build process
To build the project, please run the following command in your terminal
```mvn clean install```
This will compile, test, etc the application, will create a jar and a local docker image.
See the readme file inside the application module for instructions on how to run them.
This build process will also execute the acceptance tests against that docker image to check if still meets the requirements.
  
# assumptions
* The employee name is unique and can not be duplicated
* The system is protected for a single user with credentials "user" "password"
* Some results will be alphabetically ordered for easier testing

# todos
List of things yet to make and do
* (?) check just 2 levels up
* (+) cyclic dependencies
* (+) properly handle updates on existing organization
* (+) volume and performance tests to define system limits ant its behaviour beyond them
* (+) do not expose entities externally as json objects
* (+) ManyToOne possibility in employee to avoid expensive search of managers
* (-) make acceptance tests scenarios independent with database cleanup between them
* (-) add an index to employee name field in database
* (-) change List to Map in HierarchyRepresentation class to exactly match the contract of the test
* (-) secure/externalize authentication
* (--) more integration tests for security config
* (--) faster creation of docker image with layers
