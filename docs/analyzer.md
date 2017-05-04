# Analyzer Service

'analyzer' is a java server built on Spring and run on Tomcat. It has a RESTful HTTP API, which exposes the capability to analyze the sentiment of provided documents. The API uses JSON as it's primary information transport format.

## Build

The analyzer project builds into a runnable 'fat' jar with all the dependencies inside. That is then built into a Docker container with Java 8.

## API Docs

The API of the service is described by documentation using asciidoctor. Snippets that describe the calls are generated from special tests within the `test` source directory. Those docs test generate snippets, which are then referenced by the asciidoctor docs, which are in the `docs` source directory.

## The Analyzer

The analyzer uses the Stanford Core NLP library with english models to analyze documents. It breaks the documents down into sentences and grabs the sentiment for each sentence. It also extracts named entities from each sentence. From these, it's able to extract the overall sentiment of the document and list the entities named therein.