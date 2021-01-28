
build:
	mvn clean install

push:
	docker images
	docker push docker.io/vcosqui/organization-app:latest


