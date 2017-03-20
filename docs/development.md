# Development

This document describes the tools necessary to do development on the conjugates project

## Java

1. Install jdk 8

## Eclipse

1. Install the latest version of eclipse
2. Import and use the eclipse style settings in ide-resources/eclipse-java-google-style.xml
3. Run `./gradlew clean eclipse` and import the projects

## Docker

1. Install docker

## Builds

Conjugates uses gradle as its build system. The gradle wrapper is committed to the repo. Additionally, the build configuration uses a multi-project build.

- Unbreakable test build (deploys to AWS): `./gradlew clean check testDeploy`
- Run analyzer server: `./gradlew clean bootRun`
- Build runnable docker image: `./gradlew clean buildImage`
- Build server html documentation: `./gradlew clean asciidoctor`

### Containers
- Applications are containerized using docker
- The configuration for the servers including the Dockerfiles are in containers/service_name

## CI

Every PR to develop kicks off a CircleCI build that runs the unbreakable build.