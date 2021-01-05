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
  
# todos
List of things yet to make and do
* (+) two roots in org
* (-) change Set to HashMap in Hierarchy class to improve efficiency
* (-) change List to Map in HierarchyRepresentation class to exactly match the contract of the test
* (--) duplicated key in input map (http)
* (---) faster creation of docker image with layers
