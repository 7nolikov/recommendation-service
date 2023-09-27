FROM openjdk:17-jdk-slim-buster
MAINTAINER dmitrii_novikov
COPY target/recommendation-service-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]