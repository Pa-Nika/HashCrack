FROM openjdk:17.0.2-jdk-slim-buster

ARG EXPOSE_PORT=8080
ARG JAR_FILE=build/libs/*.jar

EXPOSE $EXPOSE_PORT
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]