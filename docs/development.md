# Development

This document describes the tools necessary to do development on the sentiment analysis project

## Java

1. Install jdk 8

## Eclipse

1. Install the latest version of Eclipse
2. Import and use the eclipse style settings in ide-resources/eclipse-java-google-style.xml
3. Run `./gradlew clean eclipse` and import the projects

## Visual Studio Code

1. Install the latest version of Visual Studio Code
2. Install tslint plugin for Visual Studio Code
3. Familiarity with Node, Webpack, and NPM is recommended

## Docker

1. Install docker

## MySQL Workbench

1. The Sentiment Analyzer uses a MySQL database. MySQL Workbench is a highly recommended for interacting with MySQL.

## Builds

The Sentiment Analyzer uses gradle as its build system. The gradle wrapper is committed to the repo. Additionally, the build configuration uses a multi-project build.

- Unbreakable test build (deploys to AWS): `./gradlew clean check testDeploy`
- Run analyzer server: `./gradlew clean :analyzer:bootRun`
- Run frontend server: `./gradlew clean runFrontend`
- Build runnable docker images: `./gradlew clean buildImage`
- Build server html documentation: `./gradlew clean asciidoctor`
- Start a local MySQL instance: `./gradlew clean startLocalDb`
- Kill a local MySQL instance: `./gradlew clean destroyLocalDb`
- Clear out local MySQL schema: `./gradlew clean flywayCleanLocal`
- Migrate local MySQL schema: `./gradlew clean flywayMigrateLocal`

### Containers
- Applications are containerized using docker
- The configuration for the servers including the Dockerfiles are in containers/service_name

## CI

Every PR to develop kicks off a CircleCI build that runs the unbreakable build.