![build](https://github.com/vcosqui/organization-test/workflows/build/badge.svg?branch=master)
[![Known Vulnerabilities](https://snyk.io/test/github/vcosqui/organization-test/badge.svg)](https://snyk.io/test/github/vcosqui/organization-test)
![Docker Image Version (latest by date)](https://img.shields.io/docker/v/vcosqui/organization-app)

# About
Test project to deliver a *java 11* *spring boot* microservice exposing a *rest* interface, the data is persisted in a *h2* database.
The project includes acceptance tests verification with *cucumber* and is delivered as a *docker* image.

# Prerequisites
Things you need to have installed
* java 11 (install via SDKMan)
* docker
* make

# Build process
To build the project, please run the following command in your terminal
```make```
This will compile, test, etc the application, will create a jar and a local docker image.
See the readme file inside the application module for instructions on how to run them.
This build process will also execute the acceptance tests against that docker image to check if still meets the requirements.