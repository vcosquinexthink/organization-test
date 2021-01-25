
build:
	mvn clean install -Dmaven.test.skip=true

push:
	docker push vcosqui/organization-app-docker:1.0.0


