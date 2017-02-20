# conjugates

A server that can analyze the Russell Conjugation of inputs

- Russell Conjugation: https://en.wikipedia.org/wiki/Emotive_conjugation
- Repo: https://github.com/kevinlr5/conjugates

## Developer Setup

### Eclipse

1. Install the latest version of eclipse
2. Import and use the eclipse style settings in ide-resources/eclipse-java-google-style.xml
3. Run `./gradlew clean eclipse` and import the projects

## Builds

Conjugates uses gradle as its build system. The gradle wrapper is committed to the repo. Additionally, the build configuration uses a multi-project build.

- Unbreakable test build: `./gradlew clean build asciidoctor`
- Run server: `./gradlew clean bootRun`
- Build runnable server jar: `./gradlew clean build`
- Build server html documentation: `./gradlew clean asciidoctor`

## CI

Every PR to develop kicks off a CircleCI build that runs the unbreakable build
