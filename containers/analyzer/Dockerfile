FROM openjdk:8

LABEL maintainer "Kevin Richards"

VOLUME /tmp
ADD app.jar app.jar
ADD application.properties application.properties
RUN bash -c 'touch /app.jar'
EXPOSE 8443
# HEALTHCHECK --interval=5s --timeout=3s --retries=5 CMD curl -k --fail https://localhost:8443/api/info/ || exit 1
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar", "--spring.config.location=application.properties"]
