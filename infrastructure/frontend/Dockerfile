FROM openjdk:8

LABEL maintainer "Kevin Richards"

VOLUME /tmp
ENV ANALYZER_API_URL http://localhost:9090
ADD app.jar app.jar
ADD application.properties application.properties
RUN bash -c 'touch /app.jar'
EXPOSE 8080
HEALTHCHECK --interval=5s --timeout=3s --retries=5 CMD curl -k --fail http://localhost:8080 || exit 1 // CircleCI incompatible
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar", "--spring.config.location=application.properties"]
