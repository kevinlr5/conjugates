FROM java:8

LABEL maintainer "Kevin Richards"

VOLUME /tmp
ADD app.jar app.jar
ADD application.properties application.properties
RUN bash -c 'touch /app.jar'
EXPOSE 8443
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar", "--spring.config.location=application.properties"]
