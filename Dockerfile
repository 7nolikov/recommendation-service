FROM openjdk:17-jdk-slim-buster
LABEL maintainer="dmitrii_novikov"
ARG JAR_FILE=target/*spring-boot.jar
WORKDIR /
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]