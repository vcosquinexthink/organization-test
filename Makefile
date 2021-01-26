
build:
	mvn clean install -Dmaven.test.skip=true

push:
        docker images
	docker push docker.io/vcosqui/organization-app-docker:latest


