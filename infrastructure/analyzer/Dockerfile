FROM openjdk:8

LABEL maintainer "Kevin Richards"

VOLUME /tmp
ADD app.jar app.jar
ADD application.properties application.properties
EXPOSE 9090 
# HEALTHCHECK --interval=5s --timeout=3s --retries=5 CMD curl -k --fail http://localhost:9090/api/info || exit 1 // CircleCI incompatible
ENTRYPOINT ["java","-Xmx1536m","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar", "--spring.config.location=application.properties"]
