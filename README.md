![build](https://github.com/vcosqui/organization-test/workflows/build/badge.svg?branch=master)
[![Known Vulnerabilities](https://snyk.io/test/github/vcosqui/organization-test/badge.svg)](https://snyk.io/test/github/vcosqui/organization-test)
![Docker Image Version (latest by date)](https://img.shields.io/docker/v/vcosqui/organization-app)

# About
Test project to deliver a *java 11* *spring boot* microservice exposing a *rest* interface, the data is persisted in a *h2* database.
The project includes aceptance tests verification with *cucumber* and is delivered as a *docker* image.

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

# features
### \#1 
I would like a pure REST API to post the JSON from Chris. I would like to have a properly formatted JSON which reflects the employee hierarchy.
### \#2 
I would like the API to be secure so that only I can use it.
### \#3 
Avoid nonsense hierarchies that contain loops or multiple roots.
### \#4 
I would really like it if the hierarchy could be stored in a relational database.
### \#5 
I want to send the name of an employee to an endpoint, and receive the name of the supervisor and the name of the supervisor’s supervisor in return.
### \#6 
Avoid nonsense hierarchies that contain loops.
### \#7 
Enable monitoring metrics.
### \#8
Add requests tracing.

# assumptions
* The employee name is unique and can not be duplicated
* The system is protected for a single user with credentials "user" "password"
* Some results will be alphabetically ordered for easier testing
* the test says explicitly 'I want to send the name of an employee to an endpoint, and receive the name of the supervisor and the name of the supervisor’s supervisor in return.' up to two levels however I decided to implement the full hierarchy up to the root employee

# todos
List of things yet to make and do
* (+) split grafana dashboard for each service instance
* (+) create a dummy business metric
* (+) create a connect another micro and connect it with organization micro (employees directory)
* (+) ci/cd to cloud (terraform? Kubernetes?)
* (+) move the infra to a different repo 
* (+) volume and performance tests to define system limits ant its behaviour beyond them
* (+) swagger doc
* (+) identify more potential errors in input
* (-) make acceptance tests scenarios independent with database cleanup between them
* (-) add an index to employee name field in database
* (-) change List to Map in HierarchyRepresentation to exactly match the contract of the requirement
* (-) secure/externalize authentication
* (--) more integration tests for security config
* (--) faster creation of docker image with layers
