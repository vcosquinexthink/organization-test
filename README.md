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

# todos
List of things yet to make and do
* (+) cyclic dependencies
* (+) properly handle updates on existing organization
* (+) volume and performance tests to define system limits ant its behaviour beyond them
* (-) more integration tests for security config
* (-) change Set to HashMap in Hierarchy class to improve efficiency
* (-) change List to Map in HierarchyRepresentation class to exactly match the contract of the test
* (-) in organization class some employee return types can be optional
* (-) secure/externalize authentication
* (--) more detailed info for user upon DuplicateRootException
* (--) make acceptance tests verification independent of the lists order, for the moment added alphabetical order for a deterministic behaviour
* (--) isRoot method in Employee should probably require an index to improve its performance
* (--) duplicated key in input map (http)
* (---) faster creation of docker image with layers
