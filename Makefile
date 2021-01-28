RELEASE_VERSION := SNAPSHOT

build:
	mvn clean build-helper:parse-version versions:set -DnewVersion=1.0-${RELEASE_VERSION} -DprocessAllModules -DgenerateBackupPoms=false
	mvn install -Drelease.version=1.0-${RELEASE_VERSION}

push:
	docker images
	docker push docker.io/vcosqui/organization-app:latest


