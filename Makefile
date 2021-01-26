
build:
	mvn clean install -Dmaven.test.skip=true

push:
	docker push docker.io/library/organization-app:latest


